package com.xkcoding.distributed.lock.aop;

import com.xkcoding.distributed.lock.annotation.DLock;
import com.xkcoding.distributed.lock.api.DistributedLockService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 分布式锁切面
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 21:02
 */
@Aspect
@RequiredArgsConstructor
public class DistributedLockAspect {
    private final DistributedLockService distributedLockService;

    @Around("@annotation(lock)")
    public Object around(ProceedingJoinPoint pjp, DLock lock) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        String lockKey = lock.lockKey();
        lockKey = parseExpression(lockKey, method, args);

        long timeout = lock.lockTime();
        TimeUnit timeUnit = lock.timeUnit();
        return distributedLockService.lock(lockKey, timeout, timeUnit, () -> {
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 解析 spel 表达式
     */
    private String parseExpression(String expression, Method method, Object[] args) {
        LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] params = nameDiscoverer.getParameterNames(method);

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }
        try {
            return parser.parseExpression(expression).getValue(context, String.class);
        } catch (SpelEvaluationException e) {
            throw new RuntimeException("spel 表达式解析错误", e);
        }
    }
}
