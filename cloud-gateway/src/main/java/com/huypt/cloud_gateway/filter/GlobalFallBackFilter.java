package com.huypt.cloud_gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.huypt.cloud_gateway.client.AuthenServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalFallBackFilter implements GlobalFilter, Ordered {
    private final ApplicationContext applicationContext;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        AuthenServiceClient authenServiceClient = applicationContext.getBean(AuthenServiceClient.class);
        try{
            JsonNode jsonNode =  authenServiceClient.authentication(request.getURI().getPath(), request.getMethod().toString());
            System.out.println(jsonNode.toString());
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
