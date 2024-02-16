package software.potthoff.aopmvp.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PerformanceLogging {

    Logger logger = LoggerFactory.getLogger(PerformanceLogging.class);
    @Pointcut("execution(* *(..))")
    public void methodsToBeProfiled() {

    }
    @Around("methodsToBeProfiled() && @annotation(software.potthoff.aopmvp.aspect.PerformanceLoggingAnnotation)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long threshold = getThreshold(joinPoint);
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        if (timeTaken > threshold) {
            logger.info("Method name: " + joinPoint.getSignature() + " time taken to execute: " + timeTaken);
        }
        return object;
    }

    // Return the threshold defined inside the annotation
    private long getThreshold(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        PerformanceLoggingAnnotation annotation = method.getAnnotation(PerformanceLoggingAnnotation.class);
        return Long.parseLong(annotation.value());
    }

    // Return method name
    private Method getMethod(JoinPoint joinPoint) {
        return ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod();
    }
}
