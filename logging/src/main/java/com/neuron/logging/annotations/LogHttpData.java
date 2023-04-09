package com.neuron.logging.annotations;

public @interface LogHttpData {
    String message() default "";

    boolean logArguments() default true;

    boolean logReturnData() default true;

    boolean logTime() default true;

}
