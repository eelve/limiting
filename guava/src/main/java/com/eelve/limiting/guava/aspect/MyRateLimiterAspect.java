package com.eelve.limiting.guava.aspect;

import com.eelve.limiting.guava.annotation.MyRateLimiter;
import com.eelve.limiting.guava.exception.BaseException;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName RateLimiterAspect
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/1/11 12:28
 * @Version 1.0
 **/
@Slf4j
@Aspect
@Component
public class MyRateLimiterAspect {
    private static final ConcurrentMap<String, RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.eelve.limiting.guava.annotation.MyRateLimiter)")
    public void MyRateLimit() {

    }

    @Around("MyRateLimit()")
    public Object pointcut(ProceedingJoinPoint point) throws Throwable {
        Object obj =null;
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 通过 AnnotationUtils.findAnnotation 获取 RateLimiter 注解
        MyRateLimiter myRateLimiter = AnnotationUtils.findAnnotation(method, MyRateLimiter.class);
        if (myRateLimiter != null && myRateLimiter.qps() > MyRateLimiter.NOT_LIMITED) {
            double qps = myRateLimiter.qps();
            String name = myRateLimiter.name();
            int executeTimeout = myRateLimiter.executeTimeout();
            if(Objects.isNull(name)){
                name = method.getName();
            }
            if (RATE_LIMITER_CACHE.get(name) == null) {
                // 初始化 QPS
                RATE_LIMITER_CACHE.put(name, RateLimiter.create(qps));
            }

            log.debug("【{}】的QPS设置为: {}", method.getName(), RATE_LIMITER_CACHE.get(name).getRate());
            Long start = System.currentTimeMillis();
            // 尝试获取令牌
            if (RATE_LIMITER_CACHE.get(method.getName()) != null && !RATE_LIMITER_CACHE.get(method.getName()).tryAcquire(myRateLimiter.timeout(), myRateLimiter.timeUnit())) {
                throw new BaseException("请求频繁，请稍后再试~");
            }
            obj = point.proceed();

            Long end = System.currentTimeMillis();
            Long executeTime = end - start;
            if((end - start) >  executeTimeout){
                log.debug("请求超时，请稍后再试~" + (end - start));
                throw new BaseException("请求超时，请稍后再试~");
            }
        }
        return obj;
    }

}
