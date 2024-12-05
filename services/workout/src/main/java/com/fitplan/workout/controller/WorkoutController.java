package com.fitplan.workout.controller;

import com.fitplan.workout.dto.WorkoutRequest;
import com.fitplan.workout.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout")
//@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createWorkout(@RequestBody WorkoutRequest workoutRequest){
        String answerAI = workoutService.createWorkout(workoutRequest);
        return "Workout created successfully. AI Feedback: " + answerAI;
    }
}
