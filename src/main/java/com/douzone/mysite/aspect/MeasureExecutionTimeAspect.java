package com.douzone.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect {
	
	@Around("execution(* *..repository.*.*(..))|| execution(* *..service.*.*(..)) || execution(* *..controller.*.*(..))") //ddd
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable{
		//before실행
		StopWatch stopWatch=new StopWatch();
		stopWatch.start();
		
		//method 실행
		Object result= pjp.proceed();
		
		//after실행
		stopWatch.stop();
		Long totalTime=stopWatch.getTotalTimeMillis();
		
		String className=pjp.getTarget().getClass().getName();
		String methodName=pjp.getSignature().getName();
		String taskName=className + "."+methodName;
		System.out.println("{ExecutionTime}["+taskName+"] "+totalTime +" mils");
		
		return result;
	}
}
