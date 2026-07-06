package com.csjmu.arena.dto;

import com.csjmu.arena.entity.EventCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MyRegistrationResponse {

    private Long registrationId;

    private Long eventId;

    private String title;

    private String venue;

    private LocalDate eventDate;

    private EventCategory category;

    private Double registrationFee;

    private String organizerName;
}