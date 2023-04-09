package com.neuron.logging.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class SpringLog {
    private Integer rank;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    private String callerClassAndMethod;
    private String currentClassAndMethod;
    private Instant invocationTime;
    private List<SpringLog> invoke = new ArrayList<>();

    public SpringLog(String callerClassAndMethod, String currentClassAndMethod, Instant invocationTime) {
        this.callerClassAndMethod = callerClassAndMethod;
        this.currentClassAndMethod = currentClassAndMethod;
        this.invocationTime = invocationTime;
    }

    public SpringLog() {
    }
}
