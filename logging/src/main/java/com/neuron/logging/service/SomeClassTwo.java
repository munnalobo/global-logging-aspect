package com.neuron.logging.service;

import org.springframework.stereotype.Service;

@Service
public class SomeClassTwo {
    public void someClassTwoPublicMethod(){
        M5();
        M6();
    }

    private void M5(){

    }


    private void M6(){
        M7();
    }

    private void M7(){

    }


}
