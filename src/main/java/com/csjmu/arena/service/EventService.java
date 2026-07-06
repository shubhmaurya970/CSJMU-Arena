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
import java.util.List;

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
    public List<EventResponse> getAllApprovedEvents() {

        List<Event> events =
                eventRepository.findByStatus(EventStatus.APPROVED);

        return events.stream()
                .map(event -> EventResponse.builder()
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
                        .build())
                .toList();
    }
    public List<EventResponse> getAllPendingEvents() {

        List<Event> events =
                eventRepository.findByStatus(EventStatus.PENDING);

        return events.stream()
                .map(event -> EventResponse.builder()
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
                        .build())
                .toList();
    }

    public EventResponse getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event not found."));

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
    public EventResponse approveEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event not found."));

        event.setStatus(EventStatus.APPROVED);

        Event updatedEvent = eventRepository.save(event);

        return EventResponse.builder()
                .id(updatedEvent.getId())
                .title(updatedEvent.getTitle())
                .description(updatedEvent.getDescription())
                .venue(updatedEvent.getVenue())
                .eventDate(updatedEvent.getEventDate())
                .registrationDeadline(updatedEvent.getRegistrationDeadline())
                .category(updatedEvent.getCategory())
                .status(updatedEvent.getStatus())
                .maxParticipants(updatedEvent.getMaxParticipants())
                .registrationFee(updatedEvent.getRegistrationFee())
                .imageUrl(updatedEvent.getImageUrl())
                .organizerName(updatedEvent.getOrganizer().getFullName())
                .build();
    }

}