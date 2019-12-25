package com.dada.config.redis.menthod;

import com.alibaba.fastjson.JSON;
import com.dada.service.base.UserService;
import com.dada.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分布式锁方法拦截切面
 */
@Aspect
@Component
@Slf4j
public class MethodLockAcpect {

  @Autowired
  private RedisService redisService;

  @Autowired
  private UserService userService;

  @Pointcut("@annotation(com.dada.config.redis.menthod.MethodLock)")
  public void redisLock() {
  }

  /** 前置通知 获取redis锁 ： 方法类全名作为 redis 的key value */
  /**
   * @param joinPoint
   * @throws CacheException
   */
  @Before("redisLock()")
  public void doBefore(JoinPoint joinPoint) throws CacheException {

    /*当前用户名称*/
    String currentUserName = userService.getCurrentUserName();

    /*设置当前锁的名称 currentUserName+*/
    String class_method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

    String lock = redisService.getLock(class_method, class_method, 2000);

    if (StringUtils.isNotBlank(lock)) {
      log.info("用户：{} 获取到锁 ：{} 有效期2ms ", currentUserName, lock);

    } else {

      log.info("用户{}： 获取锁：{}失败 ", currentUserName, class_method);
      throw new CacheException("有人你比先操作哦！");
    }
  }

  /**
   * 后置通知释放锁
   */
  @AfterReturning(returning = "ret", pointcut = "redisLock()")
  public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {

    /*当前用户名称*/
    String currentUserName = userService.getCurrentUserName();

    /*获取当前方法全名*/
    String class_method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

    /*释放锁*/
    redisService.unLock(class_method, class_method);

    log.info("用户：{} 执行方法结束方法返回值：{} 释放锁 ：{} ", currentUserName, JSON.toJSONString(ret), class_method);
  }
}
