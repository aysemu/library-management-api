package com.example.yenideen.service;

import com.example.yenideen.dto.UserCreateRequest;
import com.example.yenideen.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    void deleteUser(Long id);
}
