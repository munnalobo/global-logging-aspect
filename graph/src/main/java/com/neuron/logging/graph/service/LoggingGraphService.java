package com.neuron.logging.graph.service;

import com.neuron.logging.graph.Repository.MovieRepository;
import com.neuron.logging.graph.Repository.SpringLogRepository;
import com.neuron.logging.graph.models.Invoke;
import com.neuron.logging.graph.models.SpringLog;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@Service
public class LoggingGraphService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    SpringLogRepository springLogRepository;

    private static LinkedHashSet<SpringLog> getSpringLogs() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/venkatamaheshkumarguntur/workspace/project_neuron/utilities/test-application-logs.log"));
        AtomicInteger rank = new AtomicInteger(1);
        LinkedHashSet<SpringLog> springLogs = new LinkedHashSet<>();

        bufferedReader.lines().filter(x -> x.contains("neuron.logging.aspect.LoggingAspect    :")).forEach(x -> {
            String loggingAspectLog = x.split("neuron.logging.aspect.LoggingAspect    :")[1];
            String[] loggingAttributes = loggingAspectLog.trim().split("[|]");

            Instant logTime = Instant.parse(loggingAttributes[0].trim());
            String loggingClass = loggingAttributes[1].split(":")[1].trim();
            String loggingMethod = loggingAttributes[2].split(":")[1].trim();
            String loggingCallerMethod = loggingAttributes[3].split(":")[1].trim();

            SpringLog springLog = SpringLog.builder().invocationTime(logTime).callerClassAndMethod(loggingCallerMethod).methodName(loggingMethod).className(loggingClass).build();
            springLog.setId();
            springLogs.add(springLog);
        });

        springLogs.forEach(x -> x.setRank(rank.getAndIncrement()));

        springLogs.forEach(x -> {
            List<SpringLog> callerLogs = springLogs.stream().filter(y -> y.getCallerClassAndMethod().equals(x.getId()) && !y.getId().equals(y.getCallerClassAndMethod())).toList();
            List<Invoke> invokes = callerLogs.stream().map(y -> Invoke.builder().invocationRank(y.getRank()).springLog(y).build()).toList();
            x.setInvoke(invokes);
        });
        return springLogs;
    }

    public void saveMovie() {
        movieRepository.deleteAll();
        springLogRepository.deleteAll();
        SpringLog controller = SpringLog.builder().id("PersonController-getIdEndpoint").className("PersonController").methodName("getIdEndpoint").build();

        SpringLog serviceCAll1 = SpringLog.builder().id("PersonService-getIdEndpointMethodCall1").className("PersonService").methodName("getIdEndpointMethodCall1").build();
        SpringLog serviceCAll2 = SpringLog.builder().id("PersonService-getIdEndpointMethodCall2").className("PersonService").methodName("getIdEndpointMethodCall2").build();

        springLogRepository.saveAll(List.of(controller, serviceCAll1, serviceCAll2));

        Invoke invokeService1 = Invoke.builder().invocationRank(1).springLog(serviceCAll1).build();
        Invoke invokeService2 = Invoke.builder().invocationRank(2).springLog(serviceCAll2).build();

        controller.setInvoke(List.of(invokeService1, invokeService2));
        springLogRepository.save(controller);
    }

    @SneakyThrows
    public void saveLogNodes() {
        LinkedHashSet<SpringLog> springLogs = getSpringLogs();

        List<String> ids = springLogs.stream().map(SpringLog::getId).toList();
        List<SpringLog> allById = springLogRepository.findAllById(ids);

        allById.forEach(x -> {
            SpringLog springLog = springLogs.stream().filter(y -> x.getId().equals(y.getId())).findFirst().get();
            BeanUtils.copyProperties(springLog, x);
            springLogs.remove(springLog);
        });


        springLogRepository.deleteAll();

        AtomicInteger atomicInteger = new AtomicInteger(1);

        Map<Integer, List<Invoke>> collect = Stream.of(allById, springLogs).flatMap(Collection::stream).flatMap(x -> x.getInvoke().stream()).sorted(Comparator.comparing(Invoke::getInvocationRank)).collect(groupingBy(Invoke::getInvocationRank));
        collect.forEach((x, y) -> {
            int redefinedRank = atomicInteger.getAndIncrement();
            y.forEach(z -> z.setInvocationRank(redefinedRank));
        });

        springLogRepository.saveAll(allById);
        springLogRepository.saveAll(springLogs);
    }
}
