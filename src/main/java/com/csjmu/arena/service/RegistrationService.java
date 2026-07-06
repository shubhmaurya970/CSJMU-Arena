package com.csjmu.arena.service;

import com.csjmu.arena.repository.EventRegistrationRepository;
import com.csjmu.arena.repository.EventRepository;
import com.csjmu.arena.security.SecurityUtil;
import org.springframework.stereotype.Service;
import com.csjmu.arena.dto.RegistrationResponse;
import com.csjmu.arena.entity.Event;
import com.csjmu.arena.entity.EventRegistration;
import com.csjmu.arena.entity.EventStatus;
import com.csjmu.arena.entity.User;
import java.util.List;
import com.csjmu.arena.dto.MyRegistrationResponse;
@Service

public class RegistrationService {

    private final EventRegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final SecurityUtil securityUtil;

    public RegistrationService(
            EventRegistrationRepository registrationRepository,
            EventRepository eventRepository,
            SecurityUtil securityUtil) {

        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
        this.securityUtil = securityUtil;
    }
    public RegistrationResponse registerForEvent(Long eventId) {

        // Get currently logged-in student
        User student = securityUtil.getCurrentUser();

        // Find the event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found."));

        // Event must be approved
        if (event.getStatus() != EventStatus.APPROVED) {
            throw new RuntimeException("Event is not available for registration.");
        }

        // Check duplicate registration
        if (registrationRepository
                .findByStudentAndEvent(student, event)
                .isPresent()) {

            throw new RuntimeException("You are already registered for this event.");
        }

        // Check event capacity
        long registeredCount =
                registrationRepository.countByEvent(event);

        if (registeredCount >= event.getMaxParticipants()) {
            throw new RuntimeException("Event is full.");
        }

        // Create registration
        EventRegistration registration =
                EventRegistration.builder()
                        .student(student)
                        .event(event)
                        .build();

        EventRegistration savedRegistration =
                registrationRepository.save(registration);

        return RegistrationResponse.builder()
                .registrationId(savedRegistration.getId())
                .eventTitle(event.getTitle())
                .studentName(student.getFullName())
                .message("Registration successful.")
                .build();
    }
    public List<MyRegistrationResponse> getMyRegistrations() {

        User student = securityUtil.getCurrentUser();

        List<EventRegistration> registrations =
                registrationRepository.findByStudent(student);

        return registrations.stream()
                .map(registration -> {

                    Event event = registration.getEvent();

                    return MyRegistrationResponse.builder()
                            .registrationId(registration.getId())
                            .eventId(event.getId())
                            .title(event.getTitle())
                            .venue(event.getVenue())
                            .eventDate(event.getEventDate())
                            .category(event.getCategory())
                            .registrationFee(event.getRegistrationFee())
                            .organizerName(event.getOrganizer().getFullName())
                            .build();

                })
                .toList();
    }

}