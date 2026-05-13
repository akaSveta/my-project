package com.goncharova.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RegisterUserResponse {
    private UUID id;
    private String email;
    private Instant createdAt;
}