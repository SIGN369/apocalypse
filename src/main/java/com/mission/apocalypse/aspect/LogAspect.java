package com.mission.apocalypse.aspect;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Aspect
@Slf4j
public class LogAspect {


    @SneakyThrows
    @Around("execution(* com.mission.apocalypse.controller.*.*(..))")
    public Object logElapseTime(ProceedingJoinPoint pjp) {
        Object target = pjp.getTarget();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        log.info("{}.{}() request:{}", target, methodName, args);
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        log.info("{}.{}() response:{} elapse time:{}", target, methodName, result, (end - start));
        return result;
    }
}
