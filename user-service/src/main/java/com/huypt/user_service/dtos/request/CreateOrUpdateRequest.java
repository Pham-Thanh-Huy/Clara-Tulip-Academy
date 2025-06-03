package com.huypt.user_service.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
public class CreateOrUpdateRequest {
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private int age;

    private LocalDate birthOfDate;
}
