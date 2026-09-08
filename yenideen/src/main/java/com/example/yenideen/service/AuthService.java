package com.example.yenideen.service;

import com.example.yenideen.dto.AuthRequest;
import com.example.yenideen.dto.AuthResponse;
import com.example.yenideen.dto.RegisterRequest;
import com.example.yenideen.dto.UserResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    UserResponse register(RegisterRequest request);
}
