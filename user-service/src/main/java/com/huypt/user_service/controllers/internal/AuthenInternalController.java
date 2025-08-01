package com.huypt.user_service.controllers.internal;

import com.huypt.user_service.controllers.BaseController;
import com.huypt.user_service.dtos.CommonResponse;
import com.huypt.user_service.dtos.request.RoleResourceRequest;
import com.huypt.user_service.dtos.response.MethodResourceResponse;
import com.huypt.user_service.dtos.response.RoleResourceResponse;
import com.huypt.user_service.services.AuthenService;
import jakarta.ws.rs.GET;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class AuthenInternalController extends BaseController {
    private final AuthenService authenService;

    @PostMapping("/get-resource/by-list-role")
    public ResponseEntity<CommonResponse<RoleResourceResponse>> findListResourceByListRole(@RequestBody RoleResourceRequest request) {
        CommonResponse<RoleResourceResponse> response = authenService.findListResourceByListRole(request);
        return baseControllerResponse(response);
    }

    @GetMapping("/get-resource/by-permit-all-role")
    public ResponseEntity<CommonResponse<MethodResourceResponse>> findListResourceByPermitAllRole() {
        CommonResponse<MethodResourceResponse> response = authenService.findListResourceByPermitAllRole();
        return baseControllerResponse(response);
    }


}
