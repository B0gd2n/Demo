package com.example.demo.annotations.impl;

import com.example.demo.annotations.Cache;
import com.example.demo.common.cache.ICacheable;
import com.example.demo.common.cache.impl.CacheService;
import com.example.demo.common.cache.impl.CachedObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
public class CacheAspect {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Around(value = "@annotation(cache)", argNames = "joinPoint,cache")
    public Object roundImplementation(ProceedingJoinPoint joinPoint, final Cache cache) throws Throwable {
        String key = String.format("%s:%s", cache.key(), Stream.of(joinPoint.getArgs())
                .filter(s -> !StringUtils.isEmpty(s))
                .map(s -> s.toString())
                .collect(Collectors.joining(",")));

        ICacheable cached = CacheService.getCache(key);
        if (cached != null) return cached.getObject();
        final Object returnObject = joinPoint.proceed();
        CacheService.putCache(new CachedObject(key, returnObject, cache.time()));
        return returnObject;
    }
}
