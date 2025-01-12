package com.fitplan.microservices.exercise.service;

import com.fitplan.microservices.exercise.dto.ExerciseRequest;
import com.fitplan.microservices.exercise.dto.ExerciseResponse;
import com.fitplan.microservices.exercise.model.Exercise;
import com.fitplan.microservices.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseResponse createExercise(ExerciseRequest exerciseRequest) {
        Exercise exercise = Exercise.builder()
                .name(exerciseRequest.name())
                .description(exerciseRequest.description())
                .videoUrl(exerciseRequest.videoUrl())
                .imageUrl(exerciseRequest.imageUrl())
                .muscleGroup(exerciseRequest.muscleGroup())
                .equipment(exerciseRequest.equipment())
                .difficulty(exerciseRequest.difficulty())
                .type(exerciseRequest.type())
                .exerciseCategory(exerciseRequest.exerciseCategory())
                .build();
        exerciseRepository.save(exercise);
        log.info("Exercise created: {}", exercise);
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getVideoUrl(),
                exercise.getImageUrl(),
                exercise.getMuscleGroup(),
                exercise.getEquipment(),
                exercise.getDifficulty(),
                exercise.getType(),
                exercise.getExerciseCategory()
        );
    }

    public List<ExerciseResponse> getAllExercises() {
        return exerciseRepository.findAll()
                .stream()
                .map(exercise -> new ExerciseResponse(
                        exercise.getId(),
                        exercise.getName(),
                        exercise.getDescription(),
                        exercise.getVideoUrl(),
                        exercise.getImageUrl(),
                        exercise.getMuscleGroup(),
                        exercise.getEquipment(),
                        exercise.getDifficulty(),
                        exercise.getType(),
                        exercise.getExerciseCategory()
                ))
                .toList();
    }
}