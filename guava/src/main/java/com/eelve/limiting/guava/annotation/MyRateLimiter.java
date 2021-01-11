package com.eelve.limiting.guava.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhao.zhilue
 * @Description:
 * @date 2021/1/1112:27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRateLimiter {
    int NOT_LIMITED = 0;

    /**
     *
     * 资源名称
     */
    String name() default "";

    /**
     * qps
     */
    double qps() default NOT_LIMITED;

    /**
     * 获取令牌超时时长
     */
    int timeout() default 0;

    /**
     * 超时时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 执行超时时长
     */
    int executeTimeout() default 0;

    /**
     * 执行超时时间单位
     */
    TimeUnit executeTimeUnit() default TimeUnit.MILLISECONDS;
}
