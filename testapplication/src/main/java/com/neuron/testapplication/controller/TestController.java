package com.neuron.testapplication.controller;

import com.neuron.logging.service.ControllerServiceThree;
import com.neuron.logging.service.MunnaTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    final MunnaTestService munnaTestService;

    @Autowired
    ControllerServiceThree controllerServiceThree;

    public TestController(MunnaTestService munnaTestService) {
        this.munnaTestService = munnaTestService;
    }

    @GetMapping("/hello/{munnaTestArgumentName}")
//    @LogEveryThing(message = "Hello I am a custom message")
    public String saySomething(@PathVariable String munnaTestArgumentName) throws InterruptedException {
        munnaTestService.printSomething();
        controllerServiceThree.someServiceTwoMethodTwo();
        return "hellooooo------";
    }
}

