package com.goncharova.project.controller;

import com.goncharova.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestParam("email") String email, @RequestParam("password") String password) {
        userService.register(email, password);
        return ResponseEntity.ok("User registered successfully");
    }
}