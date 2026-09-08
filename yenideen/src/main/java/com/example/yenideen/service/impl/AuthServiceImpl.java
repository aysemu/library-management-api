package com.example.yenideen.service.impl;

import com.example.yenideen.dto.AuthRequest;
import com.example.yenideen.dto.AuthResponse;
import com.example.yenideen.dto.RegisterRequest;
import com.example.yenideen.dto.UserCreateRequest;
import com.example.yenideen.dto.UserResponse;
import com.example.yenideen.entity.User;
import com.example.yenideen.repository.UserRepository;
import com.example.yenideen.security.JwtTokenProvider;
import com.example.yenideen.service.AuthService;
import com.example.yenideen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        String role = (request.getRole() != null && !request.getRole().isBlank()) ? request.getRole() : "ROLE_USER";

        UserCreateRequest createRequest = new UserCreateRequest(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                role
        );

        return userService.createUser(createRequest);
    }
}
