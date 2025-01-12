package com.fitplan.gateway.Routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    @Value("${exercise.service.url}")
    private String exerciseServiceUrl;
    @Value("${workout.service.url}")
    private String workoutServiceUrl;
    @Value("${validation.service.url}")
    private String validationServiceUrl;

    //konfiguracija routerjev za posamezne mikrostoritve
    @Bean
    public RouterFunction<ServerResponse> exerciseServiceRoute() {

        return route("exercise")
                .route(RequestPredicates.path("/api/exercise"), HandlerFunctions.http(exerciseServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("exerciseServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> workoutServiceRoute() {

        return route("workout")
                .route(RequestPredicates.path("/api/workout"), HandlerFunctions.http(workoutServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("workoutServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> validationServiceRoute() {

        return route("validation")
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("validationServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .route(RequestPredicates.path("/api/validation"), HandlerFunctions.http(validationServiceUrl))
                .build();
    }

    //konfiguracija routerjev za swagger dokumentacijo
    @Bean
    public RouterFunction<ServerResponse> exerciseServiceSwaggerRoute() {
        return route("exercise_service_swagger")
                .route(RequestPredicates.path("/aggregate/exercise/v3/api-docs"), HandlerFunctions.http(exerciseServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("exerciseServiceSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> workoutServiceSwaggerRoute() {
        return route("workout_service_swagger")
                .route(RequestPredicates.path("/aggregate/workout/v3/api-docs"), HandlerFunctions.http(workoutServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("workoutServiceSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> validationServiceSwaggerRoute() {
        return route("validation_service_swagger")
                .route(RequestPredicates.path("/aggregate/validation/v3/api-docs"), HandlerFunctions.http(validationServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("validationServiceSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    //odziv v primeru, da mikrostoritev ni dosegljiva
    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service Unavailable, Cadia has fallen!"))
                .build();
    }
}