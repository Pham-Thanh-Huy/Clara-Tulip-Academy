package com.huypt.authen_service.controllers;

import com.huypt.authen_service.dtos.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenController {

    @PostMapping("/auth")
    public ResponseEntity<CommonResponse<String>> auth(){
        return ResponseEntity.ok(CommonResponse.success("Authen success!", null));
    }
}
