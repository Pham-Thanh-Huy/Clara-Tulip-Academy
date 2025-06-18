package com.huypt.authen_service.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huypt.authen_service.dtos.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HttpServletResponseCustom {
    private final ObjectMapper mapper;

    public void custom(HttpServletResponse response, String message, int code){
        try{
            response.setHeader("Access-Control-Allow-Originl", "*");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().println(mapper.writeValueAsString(CommonResponse.custom(null, message, code)));
            response.getWriter().flush();
        }catch (Exception e){
            log.error("[ERROR-TO-CUSTOM-RESPONSE] {}", e.getMessage());
        }
    }
}
