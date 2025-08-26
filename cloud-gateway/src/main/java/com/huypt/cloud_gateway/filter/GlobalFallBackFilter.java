package com.huypt.cloud_gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.huypt.cloud_gateway.config.ApplicationProperties;
import com.huypt.cloud_gateway.requests.AuthenServiceFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalFallBackFilter implements GlobalFilter, Ordered {
    private final ApplicationProperties applicationProperties;
    private final AuthenServiceFeign authenServiceFeign;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {;
            List<String> authHeader = exchange.getRequest().getHeaders().get("Authorization");
            String tokenAuth = authHeader == null ? null : authHeader.toString();

            JsonNode responseNode = authenServiceFeign.login(exchange.getRequest().getPath().toString(),
                   exchange.getRequest().getMethod().toString().toUpperCase(), tokenAuth); // --> nếu token Authen null tự khắc k truyền header

            int statusResponse = responseNode.get("message").get("status").asInt();
            if(statusResponse != 200){
                String responseErr = String.format("{\"message\": \"%s\", \"status\": %d}", responseNode.get("message").get("message"), statusResponse);
                exchange.getResponse().setRawStatusCode(statusResponse);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return exchange.getResponse()
                        .writeWith(Mono.just(exchange.getResponse().bufferFactory()
                                .wrap(responseErr.getBytes(StandardCharsets.UTF_8))));
            }

            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("[ERROR-IN-CLOUD-GATEWAY-FILTER] {}", e.getMessage());
            String responseErr = String.format("{\"message\": \"%s\", \"status\": %d}", "Internal Server Error!", 500);
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return exchange.getResponse()
                    .writeWith(Mono.just(exchange.getResponse()
                            .bufferFactory()
                            .wrap(responseErr.getBytes(StandardCharsets.UTF_8))));
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
