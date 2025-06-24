package com.huypt.authen_service.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.huypt.authen_service.config.ApplicationProperties;
import com.huypt.authen_service.rest.CallAPIExternal;
import com.huypt.authen_service.security.jwt.JwtTokenProvider;
import com.huypt.authen_service.security.utils.HttpServletResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final HttpServletResponseCustom servletResponseCustom;
    private final ApplicationProperties config;
    private final JwtTokenProvider tokenProvider;
    private final CallAPIExternal callAPIExternal;
    private final ObjectMapper mapper;
    @Bean
    public TokenSecurityConfig tokenSecurityConfig() {
        return new TokenSecurityConfig(servletResponseCustom, config, tokenProvider, callAPIExternal);
    }
    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
        return new RestAuthenticationEntryPoint(mapper);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        (auth) ->
                                auth.anyRequest().permitAll()
                ).exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(restAuthenticationEntryPoint());
                }).addFilterBefore(tokenSecurityConfig(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
