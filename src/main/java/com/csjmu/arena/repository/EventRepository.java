package com.csjmu.arena.repository;

import com.csjmu.arena.entity.Event;
import com.csjmu.arena.entity.EventStatus;
import com.csjmu.arena.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStatus(EventStatus status);

    List<Event> findByOrganizer(User organizer);

}