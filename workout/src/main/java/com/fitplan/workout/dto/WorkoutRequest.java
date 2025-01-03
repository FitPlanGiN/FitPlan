package com.fitplan.workout.dto;

public record WorkoutRequest(Long id, String name, String description, UserDetails userDetails) {

    public record UserDetails(String email, String firstName, String lastName) {
    }
}
