package com.huypt.authen_service.security;

import com.huypt.authen_service.config.ApplicationProperties;
import com.huypt.authen_service.dtos.response.UserAuthen;
import com.huypt.authen_service.dtos.status.ResponseStatus;
import com.huypt.authen_service.rest.CallAPIExternal;
import com.huypt.authen_service.security.jwt.JwtTokenProvider;
import com.huypt.authen_service.security.utils.HttpServletResponseCustom;
import com.huypt.authen_service.services.CustomUserDetailsService;
import com.huypt.authen_service.utils.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


@RequiredArgsConstructor
public class TokenSecurityConfig extends OncePerRequestFilter {
    private final HttpServletResponseCustom servletResponseCustom;
    private final ApplicationProperties config;
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        String authToken = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(authToken) || !authToken.startsWith(config.getTokenAuthen().getBearer())) {
            servletResponseCustom.custom(response, "Token authentication required!", ResponseStatus.UNAUTHORIZED.getStatus());
            return;
        }
        String token = authToken.substring(config.getTokenAuthen().getBearer().length()).strip();

        Map<String, Map<String, Boolean>> validateToken = tokenProvider.valiateToken(token);
        Map<String, Boolean> validateMap = validateToken.get(Constant.VALIDATE_TOKEN);
        String message = validateMap.keySet().iterator().next(); // ----> GET MESSAGE FIRST BECASUSE MAP IN VALIDATE RETURN ONE VALUE!
        if (validateMap.get(message) == Boolean.FALSE) {
            servletResponseCustom.custom(response, message, ResponseStatus.UNAUTHORIZED.getStatus());
            return;
        }
        String username = tokenProvider.parseTokenToUsername(token);
        if (ObjectUtils.isEmpty(username)) {
            servletResponseCustom.custom(response, "Token incorrect!", ResponseStatus.UNAUTHORIZED.getStatus());
            return;
        }

        // LOAD USER BY USERNAME
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(userDetails)) {
            servletResponseCustom.custom(response, "Token incorrect!", ResponseStatus.UNAUTHORIZED.getStatus());
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null,
                null, new ArrayList<>()));

        filterChain.doFilter(request, response);
    }
}
