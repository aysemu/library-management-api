package com.example.library.service;

import com.example.library.dto.AuthRequest;
import com.example.library.dto.AuthResponse;
import com.example.library.dto.RegisterRequest;
import com.example.library.dto.UserResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    UserResponse register(RegisterRequest request);
}
