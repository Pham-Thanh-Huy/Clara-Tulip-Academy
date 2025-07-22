package com.huypt.authen_service.security.dynamic;

import com.huypt.authen_service.dtos.response.UserAuthen;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/*
    - Lớp DynamicSecurityFilter kế thừa AbstractSecurityInterceptor (Bảo mật động)
    - Khi gọi beforeInvocation():
        + Load Danh sách quyền cần thiết theo url (DynamicSecurityMetadataSource)
        + Sau đó chuyển thông tin đó cho AccessDeniedManager để phân quyền!
 */
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {



    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return null;
    }


    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}
