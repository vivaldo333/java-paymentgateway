package com.finaro.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.valueOf;

@Aspect
@Component
public class LoggingAspect {

    private final ConcurrentHashMap<String, Logger> loggerMap = new ConcurrentHashMap<>();

    private Logger logger(String fqName) {
        return loggerMap.computeIfAbsent(fqName, LoggerFactory::getLogger);
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.finaro..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        if (logger(declaringTypeName).isDebugEnabled()) {
            logger(declaringTypeName).debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            logger(declaringTypeName).debug("Exit: {}() with result = {}. Execution time : {} ms", joinPoint.getSignature().getName(), result, elapsedTime);
            return result;
        } catch (IllegalArgumentException e) {
            logger(declaringTypeName).error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
            throw e;
        }
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String fqName = joinPoint.getSignature().getDeclaringTypeName();
        if (logger(fqName).isDebugEnabled()) {
            logger(fqName).error("Exception in {}(): {}. Cause: {}", joinPoint.getSignature().getName(), valueOf(e), valueOf(e.getCause()), e);
        }
    }
}
