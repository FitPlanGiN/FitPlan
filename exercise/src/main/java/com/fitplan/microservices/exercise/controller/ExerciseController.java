package com.fitplan.microservices.exercise.controller;

import com.fitplan.microservices.exercise.dto.ExerciseRequest;
import com.fitplan.microservices.exercise.dto.ExerciseResponse;
import com.fitplan.microservices.exercise.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);
    private final ExerciseService exerciseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseResponse createExercise(@RequestBody ExerciseRequest exerciseRequest) {
        logger.info("Received POST request to /api/exercise with body: {}", exerciseRequest);
        return exerciseService.createExercise(exerciseRequest);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ExerciseResponse> getAllExercises() {
        logger.info("Received GET request to /api/exercise");
        return exerciseService.getAllExercises();
    }
}
