package com.huypt.cloud_gateway.requests;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authen-serivce", url = "${application.service.authen-service.url}")
@Component
public interface AuthenServiceFeign {

    @PostMapping("/auth")
    JsonNode login(@RequestHeader(name = "X-Authen-Url") String authenUrl,
                   @RequestHeader(name = "X-Authen-Method") String authenMethod,
                   @RequestHeader(name = "Authorization", required = false) String tokenAuthen);

}
