package com.huypt.authen_service.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Resource{
    private Long id;
    private String url;
    private String name;
}
