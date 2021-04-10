package com.eelve.limiting.guava.configuration;

import com.eelve.limiting.guava.aspect.RateLimiterInterceptor;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName WebMvcConfiguration
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/1/11 12:14
 * @Version 1.0
 **/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * get接口，1秒钟生成1个令牌，也就是1秒中允许一个人访问
         */
        registry.addInterceptor(new RateLimiterInterceptor(RateLimiter.create(1, 1, TimeUnit.SECONDS)))
                .addPathPatterns("/get");
    }

}
