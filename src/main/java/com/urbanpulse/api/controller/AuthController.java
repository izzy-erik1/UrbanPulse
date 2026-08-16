package com.urbanpulse.api.controller;

import com.urbanpulse.api.dto.LoginResponse;
import com.urbanpulse.api.dto.RegisterRequest;
import com.urbanpulse.api.dto.UserResponse;
import com.urbanpulse.model.User;
import com.urbanpulse.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.urbanpulse.api.dto.LoginRequest;



import com.urbanpulse.util.JwtUtil;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        User user = authService.register(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );
        return new UserResponse(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = authService.login(request.getEmail(), request.getPassword());
        String token = JwtUtil.generateToken(user.getId(), user.getEmail());
        return new LoginResponse(new UserResponse(user), token);
    }
}