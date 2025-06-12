package com.huypt.user_service.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "application",
        ignoreInvalidFields = true
)
@Data
@AllArgsConstructor
public class ApplicationProperties {
    private TokenAuthen tokenAuthen;


    @Data
    @AllArgsConstructor
    public static class TokenAuthen{
        private String secretKey;
        private String bearer;
        private Long expiration;
    }
}
