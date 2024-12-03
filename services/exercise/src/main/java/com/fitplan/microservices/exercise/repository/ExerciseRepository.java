package com.fitplan.microservices.exercise.repository;

import com.fitplan.microservices.exercise.model.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExerciseRepository extends MongoRepository<Exercise, String> {
}
