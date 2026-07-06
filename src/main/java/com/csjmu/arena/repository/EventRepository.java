package com.csjmu.arena.repository;

import com.csjmu.arena.entity.Event;
import com.csjmu.arena.entity.EventStatus;
import com.csjmu.arena.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.csjmu.arena.entity.EventCategory;
import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStatus(EventStatus status);

    List<Event> findByOrganizer(User organizer);

    java.util.Optional<Event> findByIdAndOrganizer(Long id, User organizer);
    long countByOrganizer(User organizer);

    long countByOrganizerAndStatus(User organizer,
                                   EventStatus status);
    List<Event> findByStatusAndTitleContainingIgnoreCase(
            EventStatus status,
            String keyword);
    List<Event> findByStatusAndCategory(
            EventStatus status,
            EventCategory category);
    List<Event> findByStatusAndEventDateGreaterThanEqualOrderByEventDateAsc(
            EventStatus status,
            LocalDate eventDate);
}

