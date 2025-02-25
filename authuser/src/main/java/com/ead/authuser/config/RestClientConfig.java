package com.ead.authuser.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    private static final int TIMEOUT = 5000;

    @LoadBalanced
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder().requestFactory(customRequestFactory());
    }

    ClientHttpRequestFactory customRequestFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory
                = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout((Duration.ofMillis(TIMEOUT)));
        simpleClientHttpRequestFactory.setReadTimeout(Duration.ofMillis(TIMEOUT));
        return simpleClientHttpRequestFactory;
    }

}
