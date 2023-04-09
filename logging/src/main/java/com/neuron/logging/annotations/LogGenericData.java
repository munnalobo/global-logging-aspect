package com.neuron.logging.annotations;

public @interface LogGenericData {
    String message() default "";

    boolean logArguments() default false;

    boolean logReturnData() default false;

    boolean logTime() default true;
}
