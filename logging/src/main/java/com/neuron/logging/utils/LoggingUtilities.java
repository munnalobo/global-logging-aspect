package com.neuron.logging.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.util.HashMap;

public class LoggingUtilities {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String printMessage(String message) {
        return Strings.isBlank(message) ? "" : "Custom Message: " + message;
    }

    public static String printDuration(Duration duration) {
        return "Time Taken : " + duration + " | ";
    }

    @SneakyThrows
    public static String printReturnValue(Object returnValue) {
        return "Return Value: " + objectMapper.writeValueAsString(returnValue) + " | ";
    }

    public static String printMethodName(Method method) {
        return "Method Name: " + method.getName() + " | ";
    }

    @SneakyThrows
    public static String printArgs(Parameter[] parameters, Object[] objects) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        for (int i = 0; i < parameters.length; i++) {
            String value = parameters[i].getType().equals(String.class) ? (String) objects[i] : objectMapper.writeValueAsString(objects[i]);
            stringObjectHashMap.put(parameters[i].getName(), value);
        }

        return "Arguments : " + objectMapper.writeValueAsString(stringObjectHashMap) + " | ";
    }
}
