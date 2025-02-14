package com.geekmaster117.springweb.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAspect
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    private void logExecutionTime(ProceedingJoinPoint proceedingJoinPoint, long startTime, long endTime) throws Throwable
    {
        LOGGER.info("Method: {}.{}, Execution Time: {}ms", proceedingJoinPoint.getTarget().getClass().getName(), proceedingJoinPoint.getSignature().getName(), endTime - startTime);
    }

    @Around("execution(* com.geekmaster117.springweb.*.*.*(..))")
    public Object executionTimeAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        long startTime = System.currentTimeMillis();

        Object obj = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();

        this.logExecutionTime(proceedingJoinPoint, startTime, endTime);

        return obj;
    }
}
