package com.fitplan.workout.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

//@FeignClient(name = "validation", url = "${validation.service.url}")
@HttpExchange("/api/validation")
public interface ValidationClient {

    Logger log = LoggerFactory.getLogger(ValidationClient.class);

    @PostExchange
    @CircuitBreaker(name = "validation", fallbackMethod = "fallbackMethod")
    @Retry(name = "validation")
    String askAI(@RequestParam String name, @RequestParam String description);

    default String fallbackMethod(String name, String description, Throwable throwable) {
        log.info("Fallback triggered for workout '{}', failure reason: {}", name, throwable.getMessage());
        return "Validation AI service is not available";
    }

}
