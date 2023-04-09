package com.neuron.logging.service;

import com.neuron.logging.annotations.LogEveryThing;
import com.neuron.logging.annotations.LogGenericData;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;

import static com.neuron.logging.utils.LoggingUtilities.*;


@Slf4j
@Service
public class LogGenericDataAnnotation {
    public void logData(ProceedingJoinPoint proceedingJoinPoint, Object proceed, Duration duration) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] arguments = proceedingJoinPoint.getArgs();
        Parameter[] parameters = method.getParameters();

        LogGenericData logEveryThing = method.getAnnotation(LogGenericData.class);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(printMethodName(method));

        if (logEveryThing.logArguments()) stringBuilder.append(printArgs(parameters, arguments));
        if (logEveryThing.logReturnData()) stringBuilder.append(printReturnValue(proceed));
        if (logEveryThing.logTime()) stringBuilder.append(printDuration(duration));

        stringBuilder.append(printMessage(logEveryThing.message()));

        log.info(stringBuilder.toString());
    }
}
