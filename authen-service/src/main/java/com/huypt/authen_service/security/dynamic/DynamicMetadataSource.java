package com.huypt.authen_service.security.dynamic;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

@RequiredArgsConstructor
public class DynamicMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final SecurityService securityService;
    private Map<String, ConfigAttribute> configAttributeMap = new HashMap<>();

    @PostConstruct
    public void loadDataSource(){
        configAttributeMap = securityService.loadDataSource();
    }

    public void clearDataSource(){
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (this.configAttributeMap == null) this.loadDataSource();
        List<ConfigAttribute> configAttributes = new ArrayList<>();

        String url = ((FilterInvocation) object).getRequestUrl();
        String path = url.split("\\?")[0];

        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        while(iterator.hasNext()){
            String pattern = iterator.next();
            if(pathMatcher.match(pattern, path)){
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }

        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
