package com.fitplan.workout;

import com.fitplan.workout.stubs.ValidationClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class WorkoutApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldCreateWorkout() {
		String createWorkoutJson = """
                {
                     "name": "workout1",
                     "description": "something about working out"
                }
                """;

		//wiremock stub, ki omogoča testiranje, če je klic na /api/validation uspešen brez dejanskega klica
		ValidationClientStub.stubValidationCall("workout1", "something about working out");

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(createWorkoutJson)
				.when()
				.post("/api/workout")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

		assertThat(responseBodyString, startsWith("Workout created successfully"));
	}

}
