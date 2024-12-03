package com.fitplan.microservices.exercise.dto;

public record ExerciseRequest(
    String name,
    String description,
    String videoUrl,
    String imageUrl,
    String muscleGroup,
    String equipment,
    String difficulty,
    String type,
    String exerciseCategory
) {
}
