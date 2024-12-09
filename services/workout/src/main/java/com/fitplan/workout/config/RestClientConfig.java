package com.fitplan.workout.config;

import com.fitplan.workout.client.ValidationClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${validation.url}")
    private String validationServiceUrl;

    @Bean
    public ValidationClient validationClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(validationServiceUrl)
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(ValidationClient.class);
    }
}
