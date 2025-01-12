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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationService {

    private final RestTemplate restTemplate;

    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;


    public String askAI(String name, String description) {

        log.info("verzija4 Received askAI request for workout with name: {} and description: {}", name, description);

        // Ustvari prompt za AI
        String prompt = "Please provide feedback on the following workout:\n" +
                "Workout Name: " + name + "\n" +
                "Description: " + description;

        // Ustvari telo zahteve za OpenAI API
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini"); // Use correct model, ensure it's available
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));
        requestBody.put("temperature", 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        log.info("Poslali bomo na API");

        try {
            // Pošlje zahtevek ChatGPT-ju in pridobi odgovor
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);

            log.info("Poslali smo in čakamo");

            // Preveri, ali je odgovor prazen ali vsebuje napako
            if (response.getBody() == null) {
                log.info("Error: No response from AI");
                return "Error: No response from AI";
            }

            // Izlušči odgovor iz polja 'choices' in 'message.content'
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String aiResponse = (String) message.get("content");

            log.info(aiResponse);

            return aiResponse;
        } catch (Exception e) {
            log.info("Error: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}



