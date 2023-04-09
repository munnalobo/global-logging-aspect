package com.neuron.logging.graph.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.DynamicLabels;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("Log")
public class SpringLog {
    @Id
    private String id;
    @DynamicLabels
    @Builder.Default
    private List<String> labels = new ArrayList<>();
    private Integer rank;
    private String callerClassAndMethod;
    private String className;
    private String methodName;
    private Instant invocationTime;
    @Relationship(type = "Invoke", direction = Relationship.Direction.OUTGOING)
    private List<Invoke> invoke;

    public String getId() {
        return this.className + "." + this.methodName;
    }

    public void setId() {
        this.id = this.className + "." + this.methodName;
        this.labels.add(this.className);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpringLog springLog = (SpringLog) o;
        return Objects.equals(id, springLog.id) && Objects.equals(rank, springLog.rank) && Objects.equals(callerClassAndMethod, springLog.callerClassAndMethod) && Objects.equals(className, springLog.className) && Objects.equals(methodName, springLog.methodName) && Objects.equals(invoke, springLog.invoke);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rank, callerClassAndMethod, className, methodName, invoke);
    }
}
