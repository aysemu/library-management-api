package com.example.library.mapper;

import com.example.library.dto.UserCreateRequest;
import com.example.library.dto.UserResponse;
import com.example.library.entity.User;

public class UserMapper {

    public static User toEntity(UserCreateRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }

    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
