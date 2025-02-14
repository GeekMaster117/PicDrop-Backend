package com.geekmaster117.springweb.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidateAndUpdateAspect
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateAndUpdateAspect.class);

    private void LogNegativeIdUpdated(ProceedingJoinPoint proceedingJoinPoint)
    {
        LOGGER.info("Negative Id Updated at Method: {}.{}", proceedingJoinPoint.getTarget().getClass().getName(), proceedingJoinPoint.getSignature().getName());
    }

    @Around("execution(* com.geekmaster117.springweb.controllers.CarController.*(..))")
    public Object updateIdAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        Object[] args = proceedingJoinPoint.getArgs();

        for(int i = 0; i < args.length; ++i)
            if(args[i] instanceof Integer && (int) args[i] < 0)
            {
                args[i] = - (int) args[i];
                this.LogNegativeIdUpdated(proceedingJoinPoint);
                break;
            }

        return proceedingJoinPoint.proceed(args);
    }
}
