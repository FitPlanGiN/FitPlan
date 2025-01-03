package com.fitplan.microservices.exercise;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExerciseApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDbContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.5"));

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDbContainer.start();
	}

	@Test
	void shouldCreateExercise() {
		String requestBody = """
				{
				    "id": "ex12345",
				    "name": "Push-Up",
				    "description": "A basic upper body exercise targeting the chest, shoulders, and triceps.",
				    "videoUrl": "https://example.com/videos/push-up",
				    "imageUrl": "https://example.com/images/push-up.jpg",
				    "muscleGroup": "Chest, Shoulders, Triceps",
				    "equipment": "None",
				    "difficulty": "Intermediate",
				    "type": "Bodyweight",
				    "exerciseCategory": "Strength"
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/exercise")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Push-Up"))
				.body("description", Matchers.equalTo("A basic upper body exercise targeting the chest, shoulders, and triceps."))
				.body("videoUrl", Matchers.equalTo("https://example.com/videos/push-up"))
				.body("imageUrl", Matchers.equalTo("https://example.com/images/push-up.jpg"))
				.body("muscleGroup", Matchers.equalTo("Chest, Shoulders, Triceps"))
				.body("equipment", Matchers.equalTo("None"))
				.body("difficulty", Matchers.equalTo("Intermediate"))
				.body("type", Matchers.equalTo("Bodyweight"))
				.body("exerciseCategory", Matchers.equalTo("Strength"));
	}

}
