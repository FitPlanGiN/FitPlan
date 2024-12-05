package com.fitplan.validation.service;

import com.fitplan.validation.model.Workout;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    /*
    public String askAI(Workout workout) {
        StringBuilder response = new StringBuilder("AI thinks: ");
        for (Workout.Exercise exercise : workout.workout()) {
            response.append("\nExercise: ").append(exercise.name())
                    .append(", Muscle Group: ").append(exercise.muscleGroup());
        }
        return response.toString();
    }*/

    public String askAI(String name, String description) {
        // Placeholder logic: Return a simple AI opinion about the workout
        return "AI thinks: The workout '" + name + "' with description '" + description + "' looks great!";
    }

}
