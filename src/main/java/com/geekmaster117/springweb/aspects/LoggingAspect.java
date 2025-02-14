package com.geekmaster117.springweb.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.mapping.Join;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    private void logMethodOperation(JoinPoint joinPoint, String methodOperation)
    {
        LOGGER.info("Method {}: {}.{}", methodOperation, joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
    }

    @Before("execution(* com.geekmaster117.springweb.*.*.*(..))")
    public void methodCallAspect(JoinPoint joinPoint)
    {
        this.logMethodOperation(joinPoint, "Called");
    }

    @After("execution(* com.geekmaster117.springweb.*.*.*(..))")
    public void methodExecuteAspect(JoinPoint joinPoint)
    {
        this.logMethodOperation(joinPoint, "Executed");
    }

    @AfterThrowing("execution(* com.geekmaster117.springweb.*.*.*(..))")
    public void methodThrowAspect(JoinPoint joinPoint)
    {
        this.logMethodOperation(joinPoint, "Thrown");
    }

    @AfterReturning("execution(* com.geekmaster117.springweb.*.*.*(..))")
    public void methodReturnAspect(JoinPoint joinPoint)
    {
        this.logMethodOperation(joinPoint, "Returned");
    }
}
