package com.huypt.authen_service.security;

import com.huypt.authen_service.config.ApplicationProperties;
import com.huypt.authen_service.dtos.response.UserResponse;
import com.huypt.authen_service.dtos.status.ResponseStatus;
import com.huypt.authen_service.rest.CallAPIExternal;
import com.huypt.authen_service.security.jwt.JwtTokenProvider;
import com.huypt.authen_service.security.utils.HttpServletResponseCustom;
import com.huypt.authen_service.utils.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Map;


@Component
@RequiredArgsConstructor
public  class TokenSecurityConfig extends OncePerRequestFilter {
    private final HttpServletResponseCustom servletResponseCustom;
    private final ApplicationProperties config;
    private final JwtTokenProvider tokenProvider;
    private final CallAPIExternal callAPIExternal;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
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
        Long id = tokenProvider.parseTokenToUserId(token);
        if(ObjectUtils.isEmpty(id)){
            servletResponseCustom.custom(response, "Token incorrect!", ResponseStatus.UNAUTHORIZED.getStatus());
            return;
        }

        UserResponse userResponse = callAPIExternal.getUserById(id);
        if(ObjectUtils.isEmpty(userResponse)){
            servletResponseCustom.custom(response, "Token incorrect!", ResponseStatus.UNAUTHORIZED.getStatus());
            return;
        }

//        SecurityContex


        filterChain.doFilter(request, response);
    }
}
