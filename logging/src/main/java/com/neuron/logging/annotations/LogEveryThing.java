package com.neuron.logging.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Component
public @interface LogEveryThing {
    String message() default "";

    boolean logArguments() default true;

    boolean logReturnData() default true;

    boolean logTime() default true;
}
