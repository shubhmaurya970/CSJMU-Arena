package com.csjmu.arena.controller;

import com.csjmu.arena.dto.UserResponse;
import com.csjmu.arena.response.ApiResponse;
import com.csjmu.arena.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @GetMapping("/profile")
    public ApiResponse<UserResponse> getProfile(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Profile fetched successfully")
                .data(response)
                .build();
    }
}