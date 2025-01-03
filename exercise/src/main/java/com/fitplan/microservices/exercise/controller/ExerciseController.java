package com.fitplan.microservices.exercise.controller;

import com.fitplan.microservices.exercise.dto.ExerciseRequest;
import com.fitplan.microservices.exercise.dto.ExerciseResponse;
import com.fitplan.microservices.exercise.model.Exercise;
import com.fitplan.microservices.exercise.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseResponse createExercise(@RequestBody ExerciseRequest exerciseRequest) {
        return exerciseService.createExercise(exerciseRequest);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ExerciseResponse> getAllExercises() {
        return exerciseService.getAllExercises();
    }
}
