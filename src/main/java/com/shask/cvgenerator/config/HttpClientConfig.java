package com.shask.cvgenerator.config;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Configuration
public class HttpClientConfig {

    @Value("${graphcool.token}")
    private String AUTH_TOKEN;

    @Bean
    public HttpClient httpClient() {
        List<Header> headers = Arrays.asList(new BasicHeader(HttpHeaders.AUTHORIZATION, AUTH_TOKEN),
                new BasicHeader(HttpHeaders.ACCEPT, APPLICATION_JSON),
                new BasicHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
                );
        return HttpClients.custom()
                .setDefaultHeaders(headers)
                .build();
    }
}
