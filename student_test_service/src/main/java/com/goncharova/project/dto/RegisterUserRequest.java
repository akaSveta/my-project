package com.goncharova.project.dto;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String password;
}