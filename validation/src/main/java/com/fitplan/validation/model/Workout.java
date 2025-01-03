package com.fitplan.validation.model;

import java.util.List;

/*public record Workout(List<Exercise> workout) {



    public record Exercise(
            String id,
            String name,
            String description,
            String videoUrl,
            String imageUrl,
            String muscleGroup,
            String equipment,
            String difficulty,
            String type,
            String exerciseCategory
    ) {}
}*/

public record Workout(/*Long id,*/ String name, String description) {
}
