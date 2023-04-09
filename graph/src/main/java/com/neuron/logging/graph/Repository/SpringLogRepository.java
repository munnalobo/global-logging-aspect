package com.neuron.logging.graph.Repository;

import com.neuron.logging.graph.models.MovieEntity;
import com.neuron.logging.graph.models.SpringLog;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SpringLogRepository extends Neo4jRepository<SpringLog, String> {
}
