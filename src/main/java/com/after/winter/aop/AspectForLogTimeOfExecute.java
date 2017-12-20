package com.after.winter.aop;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j
public class AspectForLogTimeOfExecute {

  @Pointcut("@annotation(LogTimeOfExecute)")
  public void execute(){}

  @Around("execute()")
  public Object beforeExecute(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();

    Object proceed = joinPoint.proceed();

    long executeTime = System.currentTimeMillis() - start;

    log.info( joinPoint.getSignature()+" time for execute = " + executeTime + " ms");

    return proceed;
  }

}
