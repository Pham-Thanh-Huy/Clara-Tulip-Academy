package com.huypt.user_service.controllers.external;

import com.huypt.user_service.dtos.CommonResponse;
import com.huypt.user_service.dtos.request.CreateOrUpdateRequest;
import com.huypt.user_service.dtos.response.CreateOrUpdateResponse;
import com.huypt.user_service.services.AuthenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenController {
    private final AuthenService authenService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<CreateOrUpdateResponse>> register(@RequestBody @Valid CreateOrUpdateRequest
                                                                                   createOrUpdateRequest) {
        CommonResponse<CreateOrUpdateResponse> response = authenService.register(createOrUpdateRequest);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getMessage().getStatus()));
    }
}
