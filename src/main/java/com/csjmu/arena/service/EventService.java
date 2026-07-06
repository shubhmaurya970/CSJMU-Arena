package com.csjmu.arena.service;

import com.csjmu.arena.dto.CreateEventRequest;
import com.csjmu.arena.dto.EventResponse;
import com.csjmu.arena.entity.Event;
import com.csjmu.arena.entity.EventStatus;
import com.csjmu.arena.entity.User;
import com.csjmu.arena.mapper.EventMapper;
import com.csjmu.arena.repository.EventRepository;
import com.csjmu.arena.security.SecurityUtil;
import org.springframework.stereotype.Service;
import com.csjmu.arena.dto.OrganizerDashboardResponse;
import com.csjmu.arena.entity.EventCategory;
import java.util.List;
import java.time.LocalDate;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SecurityUtil securityUtil;

    public EventService(EventRepository eventRepository,
                        SecurityUtil securityUtil) {

        this.eventRepository = eventRepository;
        this.securityUtil = securityUtil;
    }

    public EventResponse createEvent(CreateEventRequest request) {

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

        return EventMapper.toResponse(savedEvent);
    }

    public List<EventResponse> getAllApprovedEvents() {

        List<Event> events =
                eventRepository.findByStatus(EventStatus.APPROVED);

        return events.stream()
                .map(EventMapper::toResponse)
                .toList();
    }

    public List<EventResponse> getAllPendingEvents() {

        List<Event> events =
                eventRepository.findByStatus(EventStatus.PENDING);

        return events.stream()
                .map(EventMapper::toResponse)
                .toList();
    }

    public EventResponse getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event not found."));

        return EventMapper.toResponse(event);
    }

    public EventResponse approveEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event not found."));

        event.setStatus(EventStatus.APPROVED);

        Event updatedEvent = eventRepository.save(event);

        return EventMapper.toResponse(updatedEvent);
    }
    public List<EventResponse> getMyEvents() {

        User organizer = securityUtil.getCurrentUser();

        List<Event> events =
                eventRepository.findByOrganizer(organizer);

        return events.stream()
                .map(EventMapper::toResponse)
                .toList();
    }
    public EventResponse rejectEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event not found."));

        event.setStatus(EventStatus.REJECTED);

        Event updatedEvent = eventRepository.save(event);

        return EventMapper.toResponse(updatedEvent);
    }
    public void deleteEvent(Long id) {

        User organizer = securityUtil.getCurrentUser();

        Event event = eventRepository
                .findByIdAndOrganizer(id, organizer)
                .orElseThrow(() ->
                        new RuntimeException("Event not found."));

        if (event.getStatus() != EventStatus.PENDING
                && event.getStatus() != EventStatus.REJECTED) {

            throw new RuntimeException(
                    "Only pending or rejected events can be deleted.");
        }

        eventRepository.delete(event);
    }
    public OrganizerDashboardResponse getOrganizerDashboard() {

        User organizer = securityUtil.getCurrentUser();

        return OrganizerDashboardResponse.builder()
                .totalEvents(
                        eventRepository.countByOrganizer(organizer))
                .pendingEvents(
                        eventRepository.countByOrganizerAndStatus(
                                organizer,
                                EventStatus.PENDING))
                .approvedEvents(
                        eventRepository.countByOrganizerAndStatus(
                                organizer,
                                EventStatus.APPROVED))
                .rejectedEvents(
                        eventRepository.countByOrganizerAndStatus(
                                organizer,
                                EventStatus.REJECTED))
                .build();
    }
    public List<EventResponse> searchEvents(String keyword) {

        List<Event> events =
                eventRepository.findByStatusAndTitleContainingIgnoreCase(
                        EventStatus.APPROVED,
                        keyword);

        return events.stream()
                .map(EventMapper::toResponse)
                .toList();
    }
    public List<EventResponse> getEventsByCategory(EventCategory category) {

        List<Event> events =
                eventRepository.findByStatusAndCategory(
                        EventStatus.APPROVED,
                        category);

        return events.stream()
                .map(EventMapper::toResponse)
                .toList();
    }
    public List<EventResponse> getUpcomingEvents() {

        List<Event> events =
                eventRepository
                        .findByStatusAndEventDateGreaterThanEqualOrderByEventDateAsc(
                                EventStatus.APPROVED,
                                LocalDate.now());

        return events.stream()
                .map(EventMapper::toResponse)
                .toList();
    }

}
