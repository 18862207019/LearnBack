package com.dada.config.redis.param;

/** 分布式锁 方法参数注解 */
import java.lang.annotation.*;

/** 方法级的注解，用于注解会产生并发问题的方法: 只能用再参数的第一个值上 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamLock {}
