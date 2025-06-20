package com.huypt.authen_service.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huypt.authen_service.config.ApplicationProperties;
import com.huypt.authen_service.dtos.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CallAPIExternal {
    private final ApplicationProperties config;
    private final ObjectMapper mapper;

    public UserResponse getUserById(Long id){
        try{
            Request request = new Request.Builder()
                    .url(String.format("%s/api/v1/get-user/%d", config.getService().getUserService(), id))
                    .get()
                    .build();
            OkHttpClient client = new OkHttpClient();

            Response response = client.newCall(request).execute();

            // -----> CHECK IF NOT SUCCESS
            int status = response.code();
            if(status != 200){
                return null;
            }

            // ------> IF SUCCESS
            JsonNode nodeResponse = mapper.readTree(response.toString());
            JsonNode data = nodeResponse.path("data");
            UserResponse userResponse = mapper.treeToValue(data, UserResponse.class);
            return userResponse;
        }catch (Exception e){
            log.error("[ERROR-TO-CALL-API-GET-USER] {}", e.getMessage());
            return null;
        }
    }
}
