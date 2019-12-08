package com.dada.config.redis.menthod;

import java.lang.annotation.*;

/**
 * 方法级的注解，用于注解会产生并发问题的方法:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLock {
}
