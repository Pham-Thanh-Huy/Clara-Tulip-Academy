package com.huypt.authen_service.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{

        }catch (Exception e){
            log.error("[ERROR-TO LO")
            return null;
        }
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthorities() {
        return List.of();
    }
}
