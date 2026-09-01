package com.salon.attendanceservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.salon.attendanceservice.service..*(..))")
    public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Started: {}.{}()", className, methodName);

        try {
            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            log.info("Completed: {}.{}() executionTime={}ms",
                    className,
                    methodName,
                    executionTime);

            return result;

        } catch (Throwable exception) {

            long executionTime = System.currentTimeMillis() - startTime;

            log.error("Failed: {}.{}() executionTime={}ms error={}",
                    className,
                    methodName,
                    executionTime,
                    exception.getMessage());

            throw exception;
        }
    }
}