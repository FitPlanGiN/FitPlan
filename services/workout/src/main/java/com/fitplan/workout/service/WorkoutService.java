package com.fitplan.workout.service;

import com.fitplan.workout.client.ValidationClient;
import com.fitplan.workout.dto.WorkoutRequest;
import com.fitplan.workout.model.Workout;
import com.fitplan.workout.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    private final ValidationClient validationClient;

    public WorkoutService(WorkoutRepository workoutRepository, ValidationClient validationClient) {
        this.workoutRepository = workoutRepository;
        this.validationClient = validationClient;
    }

    public String createWorkout(WorkoutRequest workoutRequest){
        String answerAI = validationClient.askAI(workoutRequest.name(), workoutRequest.description());
        //mapiranje WorkoutRequest na Workout object
        Workout workout = new Workout();
        workout.setName(workoutRequest.name());
        workout.setDescription(workoutRequest.description());
        workoutRepository.save(workout);
        //tukaj bo treba povezati exercise z workout z vmesno tabelo v MySQL
        return answerAI;
    }
}
