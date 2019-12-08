package com.dada.config.redis.param;

import com.dada.service.base.UserService;
import com.dada.service.redis.RedisService;
import constant.RedisContant;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/** 分布式锁方法参数拦截切面 */
@Aspect
@Component
@Slf4j
public class ParamLockAcpect {
  @Autowired private RedisService redisService;

  @Autowired private UserService userService;

  @Pointcut("@annotation(com.dada.config.redis.param.MethodParamLock)")
  public void redisLock() {}

  /** 前置通知 ：方法类全名+参数名+参数值为redis的key value*/
  @Before("redisLock()")
  public void doBefore(JoinPoint joinPoint) throws CacheException, IllegalAccessException {

    /*获取redisKey*/
    StringBuffer stringBuffer = new StringBuffer(RedisContant.REDIS_LOCK);

    /*获取当前用户名*/
    String currentUserName = userService.getCurrentUserName();

    /*获取当前redisKey和Value*/
    getLockKeyAndValue(joinPoint, stringBuffer);

    log.info("用户：{}  尝试获取锁：{}", currentUserName, stringBuffer.toString());
    String lock = redisService.getLock(stringBuffer.toString(), stringBuffer.toString(), 2000);

    if (StringUtils.isBlank(lock)) {
      log.info("用户{}： 获取锁：{}失败 ", currentUserName, stringBuffer.toString());
      throw new CacheException("有人你比先操作哦！");
    }

    log.info("用户：{}  获取锁成功：{}", currentUserName, stringBuffer.toString());
  }

  /**返回通知:方法类全名+参数名+参数值为redis的key value*/
  @AfterReturning(returning = "ret", pointcut = "redisLock()")
  public void doBefore(JoinPoint joinPoint, Object ret)
      throws CacheException, IllegalAccessException {

    /*获取redisKey*/
    StringBuffer stringBuffer = new StringBuffer(RedisContant.REDIS_LOCK);

    /*获取当前用户名*/
    String currentUserName = userService.getCurrentUserName();

    /*获取当前redisKey和Value*/
    getLockKeyAndValue(joinPoint, stringBuffer);

    redisService.unLock(stringBuffer.toString(), stringBuffer.toString());

    log.info("用户：{}  释放锁: {}成功 返回值：{}", currentUserName, stringBuffer.toString(), ret);
  }

  /**获取当前redis的Key和Value: 方法类全名+参数名+参数值为redis的key value*/
  private void getLockKeyAndValue(JoinPoint joinPoint, StringBuffer stringBuffer) throws IllegalAccessException {

    /*获取当前方法全名*/
    String class_method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

    /*拼接_方法全名*/
    stringBuffer.append("_").append(class_method);

    /*获取方法参数*/
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();

    /*获取参数对象*/
    Object[] args = joinPoint.getArgs();

    /*获取参数*/
    Parameter[] parameters = signature.getMethod().getParameters();

    for (Parameter parameter : parameters) {
      if (isPrimite(parameter.getType())) {
        ParamLock paramLock = parameter.getAnnotation(ParamLock.class);
        if (paramLock != null && parameter == null) {
          throw new CacheException(parameter.toString() + "不能为null");
        }
        /*拼接方法名称*/
        stringBuffer.append("_").append(parameter.getName()).append("_").append(parameter);
        continue;
      }
      /*获取*/
      Class<?> paramClazz = parameter.getType();

      /*获取类型所对应的参数对象，实际项目中Controller中的接口不会传两个相同的自定义类型的参数，所以此处直接使用findFirst()*/
      Object arg = Arrays.stream(args).filter(ar -> paramClazz.isAssignableFrom(ar.getClass())).findFirst().get();

      /*得到参数的所有成员变量*/
      Field[] declaredFields = paramClazz.getDeclaredFields();

      for (Field field : declaredFields) {
        field.setAccessible(true);
        /*校验标有@ParamLock注解的字段*/
        ParamLock paramLock = field.getAnnotation(ParamLock.class);
        if (paramLock != null) {
          Object fieldValue = field.get(arg);
          String fieldName = field.getName();
          if (fieldValue == null) {
            throw new CacheException(parameter.toString() + "不能为null");
          }
          stringBuffer.append("_").append(fieldName).append(":").append(fieldValue);
        }
      }
    }

    log.info("redis锁：{}", stringBuffer.toString());
  }

  /**
   * 判断是否为基本类型与八大基本数据类型的包装类
   *
   * @param clazz clazz
   * @return true：是; false：不是
   * @see java.lang.Boolean#TYPE
   * @see java.lang.Character#TYPE
   * @see java.lang.Byte#TYPE
   * @see java.lang.Short#TYPE
   * @see java.lang.Integer#TYPE
   * @see java.lang.Long#TYPE
   * @see java.lang.Float#TYPE
   * @see java.lang.Double#TYPE
   */
  private boolean isPrimite(Class<?> clazz) {
    return clazz.isPrimitive()
        || clazz == String.class
        || clazz == Boolean.class
        || clazz == Character.class
        || clazz == Byte.class
        || clazz == Short.class
        || clazz == Integer.class
        || clazz == Long.class
        || clazz == Float.class
        || clazz == Double.class;
  }
}
