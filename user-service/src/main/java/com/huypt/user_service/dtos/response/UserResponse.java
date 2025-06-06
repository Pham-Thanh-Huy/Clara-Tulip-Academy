package com.huypt.user_service.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private String username;

    private String firstName;

    private String lastName;

    private int age;

    private LocalDate birthOfDate;

    private List<String> roleName;
}
