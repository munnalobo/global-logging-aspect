package com.neuron.logging.graph.controller;

import com.neuron.logging.graph.service.LoggingGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingGraphController {

    @Autowired
    LoggingGraphService loggingGraphService;

    @GetMapping("/savePerson")
    public void savePerson() {
        loggingGraphService.saveMovie();
    }

    @GetMapping("/saveLogGraph")
    public void saveLogNodes() {
        loggingGraphService.saveLogNodes();
    }
}
