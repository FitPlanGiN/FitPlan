package com.fitplan.workout.service;

import com.fitplan.workout.client.ValidationClient;
import com.fitplan.workout.dto.WorkoutRequest;
import com.fitplan.workout.event.WorkoutCreatedEvent;
import com.fitplan.workout.model.Workout;
import com.fitplan.workout.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//nevem ce slf4j dela, ampak rabmo za kreirat logger
@Slf4j
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ValidationClient validationClient;
    //key is topic name, value is object workoutCreatedEvent
    private final KafkaTemplate<String, WorkoutCreatedEvent> kafkaTemplate;


    public String createWorkout(WorkoutRequest workoutRequest){
        String answerAI = validationClient.askAI(workoutRequest.name(), workoutRequest.description());
        //mapiranje WorkoutRequest na Workout object
        Workout workout = new Workout();
        workout.setName(workoutRequest.name());
        workout.setDescription(workoutRequest.description());
        workoutRepository.save(workout);

        //send message to Kafka Topic with id, email
        WorkoutCreatedEvent workoutCreatedEvent = new WorkoutCreatedEvent();
        workoutCreatedEvent.setId(workout.getId());
        workoutCreatedEvent.setEmail(workoutRequest.userDetails().email());
        workoutCreatedEvent.setFirstName(workoutRequest.userDetails().firstName());
        workoutCreatedEvent.setLastName(workoutRequest.userDetails().lastName());
        log.info("Start - Workout created event sent to Kafka topic workout-created: {}", workoutCreatedEvent);
        kafkaTemplate.send("workout-created", workoutCreatedEvent);
        log.info("End - Workout created event sent to Kafka topic workout-created: {}", workoutCreatedEvent);

        //tukaj bo treba povezati exercise z workout z vmesno tabelo v MySQL
        return answerAI;
    }
}
