package com.huypt.cloud_gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
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
        try{
            return null;

        }catch (Exception e){
            String responseErr = String.format("{\"message\": \"%s\", \"status\": %d}", "Internal Server Error!", 500);
            log.error("[ERROR-IN-CLOUD-GATEWAY-FILTER] {}", e.getMessage());
            return null;
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
