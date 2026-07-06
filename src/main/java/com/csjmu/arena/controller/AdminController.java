package com.csjmu.arena.controller;

import com.csjmu.arena.dto.EventResponse;
import com.csjmu.arena.response.ApiResponse;
import com.csjmu.arena.service.EventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.csjmu.arena.dto.AdminDashboardResponse;
import com.csjmu.arena.service.AdminService;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final EventService eventService;
    private final AdminService adminService;

    public AdminController(EventService eventService,
                           AdminService adminService) {

        this.eventService = eventService;
        this.adminService = adminService;

    }

    @GetMapping("/events/pending")
    public ApiResponse<List<EventResponse>> getPendingEvents() {

        List<EventResponse> events = eventService.getAllPendingEvents();

        return ApiResponse.<List<EventResponse>>builder()
                .success(true)
                .message("Pending events fetched successfully.")
                .data(events)
                .build();
    }
    @PatchMapping("/events/{id}/approve")
    public ApiResponse<EventResponse> approveEvent(
            @PathVariable Long id) {

        EventResponse response = eventService.approveEvent(id);

        return ApiResponse.<EventResponse>builder()
                .success(true)
                .message("Event approved successfully.")
                .data(response)
                .build();
    }
    @PatchMapping("/events/{id}/reject")
    public ApiResponse<EventResponse> rejectEvent(
            @PathVariable Long id) {

        EventResponse response = eventService.rejectEvent(id);

        return ApiResponse.<EventResponse>builder()
                .success(true)
                .message("Event rejected successfully.")
                .data(response)
                .build();
    }
    @GetMapping("/dashboard")
    public ApiResponse<AdminDashboardResponse> getDashboard() {

        AdminDashboardResponse response =
                adminService.getDashboardStats();

        return ApiResponse.<AdminDashboardResponse>builder()
                .success(true)
                .message("Dashboard statistics fetched successfully.")
                .data(response)
                .build();
    }


}