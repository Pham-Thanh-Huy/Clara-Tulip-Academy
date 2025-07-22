package com.huypt.authen_service.security.dynamic;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public class DynamicAccessDeniedManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        if(iterator.hasNext()){
            String authorityUser = iterator.next().getAttribute();
            for(GrantedAuthority grantedAuthority : authentication.getAuthorities()){
                if(authorityUser.trim().equals(grantedAuthority.getAuthority().trim())){
                    return;
                }
            }
        }

        throw new AccessDeniedException("You do not have access to this endpoint");
    }



    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }
}
