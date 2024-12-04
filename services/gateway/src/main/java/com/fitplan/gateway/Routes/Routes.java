package com.fitplan.gateway.Routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

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
}
