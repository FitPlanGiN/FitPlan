package com.fitplan.microservices.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "exercise")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Exercise {
    @Id
    private String id;
    private String name;
    private String description;
    private String videoUrl;
    private String imageUrl;
    private String muscleGroup;
    private String equipment;
    private String difficulty;
    private String type;
    private String exerciseCategory;
}
