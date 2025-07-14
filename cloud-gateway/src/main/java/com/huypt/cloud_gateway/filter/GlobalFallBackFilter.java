package com.huypt.cloud_gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class GlobalFallBackFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("INCOMING REQUEST {}{}", request.getMethod(), request.getURI());
        log.info("REQUEST HEADER {}", request.getHeaders());

        URI routedURI = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        if (routedURI != null) {
            log.info("URI routed: {}", routedURI);
        } else {
            log.warn("Routed URI is not yet available");
        }
    
        return chain.filter(exchange).onErrorResume(throwable -> {
            log.error("Gateway error occurred: ", throwable); //

            byte[] bytes = "{\"message\": \"Service is not available, try again later!\"}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
