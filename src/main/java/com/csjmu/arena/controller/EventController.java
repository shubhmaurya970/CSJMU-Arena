package com.csjmu.arena.controller;

import com.csjmu.arena.dto.CreateEventRequest;
import com.csjmu.arena.dto.EventResponse;
import com.csjmu.arena.response.ApiResponse;
import com.csjmu.arena.service.EventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

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
}