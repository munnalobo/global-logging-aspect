package com.neuron.testapplication.controller;

import com.neuron.logging.interceptor.SpanIdInterceptor;
import com.neuron.logging.service.ControllerServiceThree;
import com.neuron.logging.service.MunnaTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private ApplicationContext applicationContext;



    final MunnaTestService munnaTestService;

    @Autowired
    ControllerServiceThree controllerServiceThree;

    public TestController(MunnaTestService munnaTestService) {
        this.munnaTestService = munnaTestService;
    }

    @GetMapping("/hello/{munnaTestArgumentName}")
//    @LogEveryThing(message = "Hello I am a custom message")
    public String saySomething(@PathVariable String munnaTestArgumentName) throws InterruptedException {

        SpanIdInterceptor spanIdInterceptor = applicationContext.getBean(SpanIdInterceptor.class);
        if (spanIdInterceptor == null) {
            System.out.println("Bean not found in the application context");
        } else {
            System.out.println("Bean found in the application context");
        }


        munnaTestService.printSomething();
        controllerServiceThree.someServiceTwoMethodTwo();
        return "hellooooo------";
    }
}

