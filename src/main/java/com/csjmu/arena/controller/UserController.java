package com.csjmu.arena.controller;

import com.csjmu.arena.dto.RegisterRequest;
import com.csjmu.arena.dto.UserResponse;
import com.csjmu.arena.response.ApiResponse;
import com.csjmu.arena.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = userService.register(request);

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Registration Successful")
                .data(response)
                .build();
    }
}