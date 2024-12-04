package com.fitplan.workout.service;

import com.fitplan.workout.dto.WorkoutRequest;
import com.fitplan.workout.model.Workout;
import com.fitplan.workout.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public void createWorkout(WorkoutRequest workoutRequest){
        //mapiranje WorkoutRequest na Workout object
        Workout workout = new Workout();
        workout.setName(workoutRequest.name());
        workout.setDescription(workoutRequest.description());
        workoutRepository.save(workout);
        //tukaj bo treba povezati exercise z workout z vmesno tabelo v MySQL
    }
}
