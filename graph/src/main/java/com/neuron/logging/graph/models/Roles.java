package com.neuron.logging.graph.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Role")
public class Roles {
    @Id
    private String roleId;
}
