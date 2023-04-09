package com.neuron.logging.graph.Repository;

import com.neuron.logging.graph.models.MovieEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends Neo4jRepository<MovieEntity, String> {
}
