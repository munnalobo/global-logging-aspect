package com.neuron.logging.graph.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.security.PrivateKey;

@Node("Person")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonEntity {
    @Id
    private String personId;
}
