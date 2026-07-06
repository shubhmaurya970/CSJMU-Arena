package com.csjmu.arena.controller;

import com.csjmu.arena.dto.RegistrationResponse;
import com.csjmu.arena.response.ApiResponse;
import com.csjmu.arena.service.RegistrationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.csjmu.arena.dto.MyRegistrationResponse;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(
            RegistrationService registrationService) {

        this.registrationService = registrationService;
    }

    @PostMapping("/{eventId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<RegistrationResponse> registerForEvent(
            @PathVariable Long eventId) {

        RegistrationResponse response =
                registrationService.registerForEvent(eventId);

        return ApiResponse.<RegistrationResponse>builder()
                .success(true)
                .message("Successfully registered for the event.")
                .data(response)
                .build();
    }
    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<List<MyRegistrationResponse>> getMyRegistrations() {

        List<MyRegistrationResponse> response =
                registrationService.getMyRegistrations();

        return ApiResponse.<List<MyRegistrationResponse>>builder()
                .success(true)
                .message("Registrations fetched successfully.")
                .data(response)
                .build();
    }
}