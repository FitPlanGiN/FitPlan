package com.fitplan.microservices.exercise;

import org.springframework.boot.SpringApplication;

public class TestExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.from(ExerciseApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
