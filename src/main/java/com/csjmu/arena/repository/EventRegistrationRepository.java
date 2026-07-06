package com.csjmu.arena.repository;

import com.csjmu.arena.entity.Event;
import com.csjmu.arena.entity.EventRegistration;
import com.csjmu.arena.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository
        extends JpaRepository<EventRegistration, Long> {

    // Check if a student has already registered for an event
    Optional<EventRegistration> findByStudentAndEvent(User student, Event event);

    // Get all registrations of a student
    List<EventRegistration> findByStudent(User student);

    // Count registrations for an event
    long countByEvent(Event event);
}