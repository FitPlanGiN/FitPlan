package com.fitplan.microservices.exercise.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI exerciseServiceAPI(){
        return new OpenAPI()
                .info(new Info().title("Exercise Service API")
                        .description("Exercise Service API implemented using springdoc-openapi and spring-boot")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Exercise Service External Documentation")
                        .url("https://example.com"));
    }
}
