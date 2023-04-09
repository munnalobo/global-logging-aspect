package com.neuron.logging.service;

import com.neuron.logging.interceptor.SpanIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunnaTestService {

    @Autowired
    SpanIdInterceptor spanIdInterceptor;


    @Autowired
    SomeClassTwo someClassTwo;

    public void printSomething() {
        printLol();
        M2();
        M3();
        someClassTwo.someClassTwoPublicMethod();
    }

    //    @LogEveryThing(message = "Private method -> Hello I am a custom message")
    private void printLol() {
        System.out.println("some thing to print --------------------------");
    }

    private void M2() {
        System.out.println("M2");
    }

    private void M3() {
        System.out.println("M3");
        MM4();
    }

    private void MM4() {
        System.out.println("M3");
    }
}
