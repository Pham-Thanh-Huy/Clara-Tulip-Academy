package com.huypt.authen_service.security.dynamic;

import com.huypt.authen_service.dtos.status.ResponseStatus;
import com.huypt.authen_service.security.config.IgnoreUrlsConfig;
import com.huypt.authen_service.security.utils.HttpServletResponseCustom;
import com.huypt.authen_service.utils.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Locale;

@RequiredArgsConstructor
public class DynamicSecurityFilter extends OncePerRequestFilter {
    private final HttpServletResponseCustom httpServletResponseCustom;
    private final IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String urlAuthen = request.getHeader(Constant.X_AUTHEN_URL);
        String methodAuthen =  request.getHeader(Constant.X_AUTHEN_METHOD);

        if (ObjectUtils.isEmpty(urlAuthen)) {
            httpServletResponseCustom.custom(response,
                    "Authentication failed. Please send the request header and include the URL in it (X-Authen-Url)!",
                    ResponseStatus.UNAUTHORIZED.getStatus());
            return;
        }

        if (ObjectUtils.isEmpty(methodAuthen)){
            httpServletResponseCustom.custom(response,
                    "Authentication failed. Please send the request header and include the method in it (X-Authen-Method)!",
                    ResponseStatus.UNAUTHORIZED.getStatus());
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> grantedAuthorities = usernamePasswordAuthenticationToken.getAuthorities();

        if(request.getMethod().equals(HttpMethod.OPTIONS.toString())){
            filterChain.doFilter(request, response);
            return;
        }

        for(String ignoreUrl : ignoreUrlsConfig.getUrls()){
            if(antPathMatcher.match(ignoreUrl, urlAuthen)){
                filterChain.doFilter(request, response);
                return;
            }
        }

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            if(antPathMatcher.match(grantedAuthority.getAuthority(), urlAuthen) &&
                    request.getMethod().toUpperCase().equals(methodAuthen)){
                filterChain.doFilter(request, response);
                return;
            }
        }

        httpServletResponseCustom.custom(response, "You not have permission to access this endpoint!", ResponseStatus.UNAUTHORIZED.getStatus());
    }
}
