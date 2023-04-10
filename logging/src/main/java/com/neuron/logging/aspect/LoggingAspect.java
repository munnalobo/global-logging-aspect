package com.neuron.logging.aspect;


import com.neuron.logging.interceptor.SpanIdInterceptor;
import com.neuron.logging.models.SpringLog;
import com.neuron.logging.service.LogEverThingAnnotation;
import com.neuron.logging.service.LogGenericDataAnnotation;
import com.neuron.logging.service.LogHttpDataAnnotation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@SuppressAjWarnings({"adviceDidNotMatch"})
@Aspect
@Order(2)
@DependsOn("spanIdInterceptor")
@Service
public class LoggingAspect {

    @Autowired
    SpanIdInterceptor spanIdInterceptor;

    public List<SpringLog> springLogs = new ArrayList<>();

    private static Object proceedNext(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception ex) {
            log.error("something ");
            throw ex;
        }
    }

    @Around("(execution(private * * (..)) || execution(* * (..))) && !target(com.neuron.logging.models.SpringLog)")
    public Object intercept(final ProceedingJoinPoint point) throws Throwable {
        try {
//            SpanIdInterceptor spanIdInterceptor = applicationContext.getBean(SpanIdInterceptor.class);
            if (Objects.nonNull(point.getTarget())) {
                Class<?> declaringType = point.getSignature().getDeclaringType();
                final Method method = ((MethodSignature) point.getSignature()).getMethod();
                String mName = method.getName();
                String cName = method.getDeclaringClass().getSimpleName();
//                String spanId = spanIdInterceptor.getSpanId();
                if (declaringType.getName().contains("com.neuron") && !cName.contains("$")) {
                    List<String> classnameList = Arrays.stream(Thread.currentThread().getStackTrace()).filter(x -> (x.getClassName().contains("com.neuron")) && (!x.getClassName().equals("com.neuron.logging.aspect.LoggingAspect")) && (!x.getMethodName().equals("run")) && (!x.getMethodName().contains("_aroundBody"))).map(x -> {
                        String[] split = x.getClassName().split("[.]");
                        return split[split.length - 1] + "." + x.getMethodName();
                    }).toList();
                    String callingMethod = classnameList.size() > 1 ? classnameList.get(1) : classnameList.get(0);
                    SpringLog springLog = new SpringLog(callingMethod, cName + "." + mName, Instant.now());
                    springLog.setRank(1);
                    springLogs.add(springLog);
                    log.info("{} | class: {} | method: {} | calling-method: {} | span-id: {}", Instant.now(), cName, mName, callingMethod, "spanId");
                }
            }
        } catch (Exception e) {
            log.error("Some Exception Happened From Logging Aspect:", e);
        }
        return point.proceed();
    }

    /*

    @Around("execution(private * * (..)) || execution(* * (..))")
    public Object intercept(final ProceedingJoinPoint point) throws Throwable {
        try {
            if (Objects.nonNull(point.getTarget())) {
                Class<?> declaringType = point.getSignature().getDeclaringType();
                final Method method = ((MethodSignature) point.getSignature()).getMethod();
                String mName = method.getName();
                String cName = method.getDeclaringClass().getSimpleName();
                if (declaringType.getName().contains("com.neuron") && !cName.contains("$")) {
                    List<String> classnameList = Arrays.stream(Thread.currentThread().getStackTrace()).filter(x -> (x.getClassName().contains("com.neuron")) && (!x.getClassName().equals("com.neuron.logging.aspect.LoggingAspect")) && (!x.getMethodName().equals("run")) && (!x.getMethodName().contains("_aroundBody"))).map(x -> {
                        String[] split = x.getClassName().split("[.]");
                        return split[split.length - 1] + "." + x.getMethodName();
                    }).toList();
                    String callingMethod = classnameList.size() > 1 ? classnameList.get(1) : classnameList.get(0);
                    log.info("{} | class: {} | method: {} | calling-method: {}", Instant.now(), cName, mName, callingMethod);
                }
            }
        } catch (Exception e) {
            log.error("Some Exception Happened From Logging Aspect:", e);
        }
        return point.proceed();
    }*/

    @Around("@annotation(com.neuron.logging.annotations.LogEveryThing) && execution(* *(..))")
    public Object logEveryThing(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Instant start = Instant.now();
        Object proceed = proceedNext(proceedingJoinPoint);

        try {
            Instant stop = Instant.now();
            Duration duration = Duration.between(start, stop);
            LogEverThingAnnotation logEverThingAnnotation = new LogEverThingAnnotation();
            logEverThingAnnotation.logData(proceedingJoinPoint, proceed, duration);
        } catch (Exception e) {
            log.error(" Error Occurred While Logging With Library");
        }
        return proceed;
    }

    @Around("@annotation(com.neuron.logging.annotations.LogGenericData) && execution(* *(..))")
    public Object logGenericData(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Instant start = Instant.now();
        Object proceed = proceedNext(proceedingJoinPoint);

        try {
            Instant stop = Instant.now();
            Duration duration = Duration.between(start, stop);
            LogGenericDataAnnotation logGenericDataAnnotation = new LogGenericDataAnnotation();
            logGenericDataAnnotation.logData(proceedingJoinPoint, proceed, duration);
        } catch (Exception e) {
            log.error(" Error Occurred While Logging With Library");
        }
        return proceed;
    }

    @Around("@annotation(com.neuron.logging.annotations.LogHttpData) && execution(* *(..))")
    public Object logHttpData(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Instant start = Instant.now();
        Object proceed = proceedNext(proceedingJoinPoint);

        try {
            Instant stop = Instant.now();
            Duration duration = Duration.between(start, stop);
            LogHttpDataAnnotation logHttpDataAnnotation = new LogHttpDataAnnotation();
            logHttpDataAnnotation.logData(proceedingJoinPoint, proceed, duration);
        } catch (Exception e) {
            log.error(" Error Occurred While Logging With Library");
        }
        return proceed;
    }
}
