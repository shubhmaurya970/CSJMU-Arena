package com.csjmu.arena.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.csjmu.arena.entity.Role;

@Data
public class RegisterRequest {

    @NotBlank
    private String fullName;

    @Email
    private String email;

    @NotBlank
    private String password;

    private Role role;

}