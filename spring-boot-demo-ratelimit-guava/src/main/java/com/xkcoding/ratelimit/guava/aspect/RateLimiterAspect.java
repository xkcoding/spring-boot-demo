package com.xkcoding.ratelimit.guava.aspect;

import com.xkcoding.ratelimit.guava.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <p>
 * 限流切面
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/12 14:27
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {
    private static final com.google.common.util.concurrent.RateLimiter RATE_LIMITER = com.google.common.util.concurrent.RateLimiter.create(Double.MAX_VALUE);

    @Pointcut("@annotation(com.xkcoding.ratelimit.guava.annotation.RateLimiter)")
    public void rateLimit() {

    }

    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 通过 AnnotationUtils.findAnnotation 获取 RateLimiter 注解
        RateLimiter rateLimiter = AnnotationUtils.findAnnotation(method, RateLimiter.class);
        if (rateLimiter != null && rateLimiter.qps() > RateLimiter.NOT_LIMITED) {
            double qps = rateLimiter.qps();
            log.debug("【{}】的QPS设置为: {}", method.getName(), qps);
            // 重新设置 QPS
            RATE_LIMITER.setRate(qps);
            // 尝试获取令牌
            if (!RATE_LIMITER.tryAcquire(rateLimiter.timeout(), rateLimiter.timeUnit())) {
                throw new RuntimeException("手速太快了，慢点儿吧~");
            }
        }
        return point.proceed();
    }
}
