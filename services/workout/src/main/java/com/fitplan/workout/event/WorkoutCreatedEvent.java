package com.fitplan.workout.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutCreatedEvent {
    private Long id;
    private String email;
}
