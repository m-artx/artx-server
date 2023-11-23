package com.artx.artx.etc.util.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@Profile("local")
public class LogAop {

	@Pointcut("execution(* com.artx..*Controller.*(..))")
	public void pointCut(){

	}

	@Around("pointCut()")
	public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;

		String controllerName = joinPoint.getSignature().getDeclaringTypeName().substring(joinPoint.getSignature().getDeclaringTypeName().lastIndexOf(".") + 1);
		String methodName = joinPoint.getSignature().getName();
		log.info("[{}] {} = {}ms", controllerName, methodName, executionTime);

		return result;
	}

}
