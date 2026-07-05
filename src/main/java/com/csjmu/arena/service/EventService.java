package com.csjmu.arena.service;

import com.csjmu.arena.dto.CreateEventRequest;
import com.csjmu.arena.dto.EventResponse;
import com.csjmu.arena.entity.Event;
import com.csjmu.arena.entity.EventStatus;
import com.csjmu.arena.entity.User;
import com.csjmu.arena.repository.EventRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.csjmu.arena.security.SecurityUtil;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SecurityUtil securityUtil;

    public EventService(EventRepository eventRepository,
                        SecurityUtil securityUtil) {

        this.eventRepository = eventRepository;
        this.securityUtil = securityUtil;
    }

    public EventResponse createEvent(CreateEventRequest request){

        User organizer = securityUtil.getCurrentUser();

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .venue(request.getVenue())
                .eventDate(request.getEventDate())
                .registrationDeadline(request.getRegistrationDeadline())
                .category(request.getCategory())
                .maxParticipants(request.getMaxParticipants())
                .registrationFee(request.getRegistrationFee())
                .imageUrl(request.getImageUrl())
                .organizer(organizer)
                .status(EventStatus.PENDING)
                .build();

        Event savedEvent = eventRepository.save(event);

        return EventResponse.builder()
                .id(savedEvent.getId())
                .title(savedEvent.getTitle())
                .description(savedEvent.getDescription())
                .venue(savedEvent.getVenue())
                .eventDate(savedEvent.getEventDate())
                .registrationDeadline(savedEvent.getRegistrationDeadline())
                .category(savedEvent.getCategory())
                .status(savedEvent.getStatus())
                .maxParticipants(savedEvent.getMaxParticipants())
                .registrationFee(savedEvent.getRegistrationFee())
                .imageUrl(savedEvent.getImageUrl())
                .organizerName(savedEvent.getOrganizer().getFullName())
                .build();
    }
}