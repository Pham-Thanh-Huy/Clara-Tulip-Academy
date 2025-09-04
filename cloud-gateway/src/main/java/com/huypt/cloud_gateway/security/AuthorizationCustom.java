package com.huypt.cloud_gateway.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.huypt.cloud_gateway.config.ApplicationProperties;
import com.huypt.cloud_gateway.logging.CurlLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthorizationCustom {
    private final ApplicationProperties config;

    public Mono<JsonNode> authenRequestService(String path, String method, String token) {
        WebClient webClient = WebClient.builder()
                .baseUrl(config.getService().getAuthenService().getUrl())
                .filter(CurlLoggingFilter.curlAuthenLogging())
                .build();

        return webClient.post()
                .uri("/auth")
                .headers(headers -> {
                            headers.set("X-Authen-Url", path);
                            headers.set("X-Authen-Method", method.toUpperCase());
                            if (!ObjectUtils.isEmpty(token)) {
                                headers.set(HttpHeaders.AUTHORIZATION, token);
                            }
                        }
                )
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response ->
                        response.bodyToMono(JsonNode.class));
    }
}
