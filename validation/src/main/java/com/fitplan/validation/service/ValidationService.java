package com.fitplan.validation.service;

import com.fitplan.validation.model.Workout;
import org.springframework.stereotype.Service;

import com.fitplan.validation.model.Workout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ValidationService {

    private final RestTemplate restTemplate;

    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    public ValidationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String askAI(String name, String description) {
        // Ustvari prompt za AI
        String prompt = "Please provide feedback on the following workout:\n" +
                "Workout Name: " + name + "\n" +
                "Description: " + description;

        // Ustvari telo zahteve za OpenAI API
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4");  // Lahko uporabiš tudi gpt-3.5-turbo
        requestBody.put("messages", new Object[]{
                Map.of("role", "user", "content", prompt)
        });
        requestBody.put("temperature", 0.7);

        // Nastavi glave zahteve
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Pošlji zahtevek ChatGPT-ju in pridobi odgovor
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);

        // Izlušči odgovor iz polja 'choices'
        Map<String, Object> choices = (Map<String, Object>) ((List<Object>) response.getBody().get("choices")).get(0);
        String aiResponse = (String) ((Map<String, Object>) choices.get("message")).get("content");

        return aiResponse;
    }
}



/*@Service
public class ValidationService {


    public String askAI(Workout workout) {
        StringBuilder response = new StringBuilder("AI thinks: ");
        for (Workout.Exercise exercise : workout.workout()) {
            response.append("\nExercise: ").append(exercise.name())
                    .append(", Muscle Group: ").append(exercise.muscleGroup());
        }
        return response.toString();
    }

    public String askAI(String name, String description) {
        // Placeholder logic: Return a simple AI opinion about the workout
        return "AI thinks: The workout '" + name + "' with description '" + description + "' looks great!";
    }

}*/
