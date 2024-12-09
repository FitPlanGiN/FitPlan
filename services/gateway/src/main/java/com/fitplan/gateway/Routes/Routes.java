package com.fitplan.gateway.Routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class Routes {

    //konfiguracija routerjev za posamezne mikrostoritve
    @Bean
    public RouterFunction<ServerResponse> exerciseServiceRoute() {

        return GatewayRouterFunctions.route("exercise")
                .route(RequestPredicates.path("/api/exercise"), HandlerFunctions.http("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> workoutServiceRoute() {

        return GatewayRouterFunctions.route("workout")
                .route(RequestPredicates.path("/api/workout"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> validationServiceRoute() {

        return GatewayRouterFunctions.route("validation")
                .route(RequestPredicates.path("/api/validation"), HandlerFunctions.http("http://localhost:8082"))
                .build();
    }

    //konfiguracija routerjev za swagger dokumentacijo
    @Bean
    public RouterFunction<ServerResponse> exerciseServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("exercise_service_swagger")
                .route(RequestPredicates.path("/aggregate/exercise/v3/api-docs"), HandlerFunctions.http("http://localhost:8080"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> workoutServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("workout_service_swagger")
                .route(RequestPredicates.path("/aggregate/workout/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> validationServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("validation_service_swagger")
                .route(RequestPredicates.path("/aggregate/validation/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }
}
