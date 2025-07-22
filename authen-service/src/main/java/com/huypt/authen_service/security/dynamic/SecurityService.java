package com.huypt.authen_service.security.dynamic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huypt.authen_service.client.UserFeignClient;
import com.huypt.authen_service.dtos.response.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SecurityService {
    private final UserFeignClient userFeignClient;
    private final ObjectMapper mapper;

    @Bean
    public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new HashMap<>();
        JsonNode resourceNode = userFeignClient.getAllResource().get("data");
        List<Resource> resources = mapper.convertValue(resourceNode, new TypeReference<List<Resource>>() {
        });

        resources.forEach(
                resource -> map.put(resource.getUrl(), new SecurityConfig(resource.getId() + ":" + resource.getName()))
        );

        return map;
    }
}
