package com.huypt.user_service.services;

import com.huypt.user_service.dtos.CommonResponse;
import com.huypt.user_service.dtos.request.CreateOrUpdateUserRequest;
import com.huypt.user_service.dtos.response.UserResponse;
import com.huypt.user_service.models.Profile;
import com.huypt.user_service.models.Role;
import com.huypt.user_service.models.User;
import com.huypt.user_service.repositories.RoleRepository;
import com.huypt.user_service.repositories.UserRepository;
import com.huypt.user_service.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CommonResponse<UserResponse> register(CreateOrUpdateUserRequest request) {
        try {
            User userExistByUsername = userRepository.findByUsername(request.getUsername()).orElse(null);
            if (!ObjectUtils.isEmpty(userExistByUsername)) {
                return CommonResponse.badRequest(null, Constant.USERNAME_EXISTED);
            }

            Role role = roleRepository.findByName(Constant.USER).orElseGet(() -> Role.builder().name(Constant.USER).build());

            // ----> FORMAT DATE
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthOfDate = LocalDate.parse(request.getBirthOfDate(), timeFormatter);

            User user = User.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .build();
            user.setRelationProfile(Profile.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .birthOfDate(birthOfDate)
                    .age(request.getAge())
                    .build());
            user.setRelationRole(Collections.singletonList(role));

            User userSave = userRepository.save(user);
            UserResponse response = UserResponse.builder()
                    .username(userSave.getUsername())
                    .firstName(userSave.getProfile().getFirstName())
                    .lastName(userSave.getProfile().getLastName())
                    .age(userSave.getProfile().getAge())
                    .birthOfDate(userSave.getProfile().getBirthOfDate())
                    .roleName(userSave.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .build();
            return CommonResponse.success(response, "Thêm user thành công!");
        } catch (Exception e) {
            log.error("[ERROR-TO-REGISTER] {}", e.getMessage());
            return CommonResponse.internalServerError(null, null);
        }
    }




}
