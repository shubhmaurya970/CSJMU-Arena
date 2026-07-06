package com.csjmu.arena.mapper;

import com.csjmu.arena.dto.EventResponse;
import com.csjmu.arena.entity.Event;

public class EventMapper {

    public static EventResponse toResponse(Event event) {

        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .venue(event.getVenue())
                .eventDate(event.getEventDate())
                .registrationDeadline(event.getRegistrationDeadline())
                .category(event.getCategory())
                .status(event.getStatus())
                .maxParticipants(event.getMaxParticipants())
                .registrationFee(event.getRegistrationFee())
                .imageUrl(event.getImageUrl())
                .organizerName(event.getOrganizer().getFullName())
                .build();
    }

}