package com.huypt.user_service.controllers.internal;

import com.huypt.user_service.controllers.BaseController;
import com.huypt.user_service.dtos.CommonResponse;
import com.huypt.user_service.dtos.request.RoleResourceRequest;
import com.huypt.user_service.dtos.response.RoleResourceResponse;
import com.huypt.user_service.services.AuthenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class AuthenInternalController extends BaseController {
    private final AuthenService authenService;

    @PostMapping("/get-resource/by-list-role")
    public ResponseEntity<CommonResponse<RoleResourceResponse>> findListResourceByListRole(@RequestBody RoleResourceRequest request){
        CommonResponse<RoleResourceResponse> response = authenService.findListResourceByListRole(request);
        return baseControllerResponse(response);
    }


}
