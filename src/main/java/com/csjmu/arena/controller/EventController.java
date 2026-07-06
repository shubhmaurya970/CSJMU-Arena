package com.csjmu.arena.controller;

import com.csjmu.arena.dto.CreateEventRequest;
import com.csjmu.arena.dto.EventResponse;
import com.csjmu.arena.response.ApiResponse;
import com.csjmu.arena.service.EventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.csjmu.arena.dto.OrganizerDashboardResponse;
import java.util.List;
import com.csjmu.arena.entity.EventCategory;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping
    public ApiResponse<EventResponse> createEvent(
            @Valid @RequestBody CreateEventRequest request) {

        EventResponse response =
                eventService.createEvent(request);

        return ApiResponse.<EventResponse>builder()
                .success(true)
                .message("Event submitted for approval.")
                .data(response)
                .build();
    }
    @GetMapping
    public ApiResponse<List<EventResponse>> getAllApprovedEvents() {

        List<EventResponse> events = eventService.getAllApprovedEvents();

        return ApiResponse.<List<EventResponse>>builder()
                .success(true)
                .message("Events fetched successfully.")
                .data(events)
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<EventResponse> getEventById(
            @PathVariable Long id) {

        EventResponse response = eventService.getEventById(id);

        return ApiResponse.<EventResponse>builder()
                .success(true)
                .message("Event fetched successfully.")
                .data(response)
                .build();
    }
    @GetMapping("/my-events")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ApiResponse<List<EventResponse>> getMyEvents() {

        List<EventResponse> events = eventService.getMyEvents();

        return ApiResponse.<List<EventResponse>>builder()
                .success(true)
                .message("Your events fetched successfully.")
                .data(events)
                .build();
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ApiResponse<Void> deleteEvent(
            @PathVariable Long id) {

        eventService.deleteEvent(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Event deleted successfully.")
                .build();
    }
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ApiResponse<OrganizerDashboardResponse> getOrganizerDashboard() {

        OrganizerDashboardResponse response =
                eventService.getOrganizerDashboard();

        return ApiResponse.<OrganizerDashboardResponse>builder()
                .success(true)
                .message("Dashboard statistics fetched successfully.")
                .data(response)
                .build();
    }
    @GetMapping("/search")
    public ApiResponse<List<EventResponse>> searchEvents(
            @RequestParam String keyword) {

        List<EventResponse> response =
                eventService.searchEvents(keyword);

        return ApiResponse.<List<EventResponse>>builder()
                .success(true)
                .message("Search completed successfully.")
                .data(response)
                .build();
    }
    @GetMapping("/category/{category}")
    public ApiResponse<List<EventResponse>> getEventsByCategory(
            @PathVariable EventCategory category) {

        List<EventResponse> response =
                eventService.getEventsByCategory(category);

        return ApiResponse.<List<EventResponse>>builder()
                .success(true)
                .message("Events fetched successfully.")
                .data(response)
                .build();
    }
    @GetMapping("/upcoming")
    public ApiResponse<List<EventResponse>> getUpcomingEvents() {

        List<EventResponse> response =
                eventService.getUpcomingEvents();

        return ApiResponse.<List<EventResponse>>builder()
                .success(true)
                .message("Upcoming events fetched successfully.")
                .data(response)
                .build();
    }

}