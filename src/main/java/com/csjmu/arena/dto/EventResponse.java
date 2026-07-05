package com.csjmu.arena.dto;

import com.csjmu.arena.entity.EventCategory;
import com.csjmu.arena.entity.EventStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long id;

    private String title;

    private String description;

    private String venue;

    private LocalDate eventDate;

    private LocalDate registrationDeadline;

    private EventCategory category;

    private EventStatus status;

    private Integer maxParticipants;

    private Double registrationFee;

    private String imageUrl;

    private String organizerName;
}