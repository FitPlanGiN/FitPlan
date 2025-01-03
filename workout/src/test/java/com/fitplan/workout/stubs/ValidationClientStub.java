package com.fitplan.workout.stubs;

import lombok.experimental.UtilityClass;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
@UtilityClass
public class ValidationClientStub {

    public static void stubValidationCall(String name, String description) {
        stubFor(post(urlEqualTo("/api/validation"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("""
                        {
                            "name": "%s",
                            "description": "%s"
                        }
                        """.formatted(name, description)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("\"AI thinks: The workout '" + name + "' is well-designed!\"")));
    }
}

