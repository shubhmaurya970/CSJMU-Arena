package com.csjmu.arena.service;

import com.csjmu.arena.repository.EventRepository;
import com.csjmu.arena.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.csjmu.arena.dto.AdminDashboardResponse;
import com.csjmu.arena.entity.EventStatus;
import com.csjmu.arena.entity.Role;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public AdminService(UserRepository userRepository,
                        EventRepository eventRepository) {

        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }
    public AdminDashboardResponse getDashboardStats() {

        return AdminDashboardResponse.builder()

                .totalUsers(userRepository.count())

                .totalStudents(
                        userRepository.countByRole(Role.STUDENT))

                .totalOrganizers(
                        userRepository.countByRole(Role.ORGANIZER))

                .totalAdmins(
                        userRepository.countByRole(Role.ADMIN))

                .totalEvents(
                        eventRepository.count())

                .pendingEvents(
                        eventRepository.findByStatus(EventStatus.PENDING).size())

                .approvedEvents(
                        eventRepository.findByStatus(EventStatus.APPROVED).size())

                .rejectedEvents(
                        eventRepository.findByStatus(EventStatus.REJECTED).size())

                .build();
    }
}