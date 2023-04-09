package com.neuron.logging.graph.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class Invoke {

    @Id @GeneratedValue Long id;
    private Integer invocationRank;
    @TargetNode
    private SpringLog springLog;
}
