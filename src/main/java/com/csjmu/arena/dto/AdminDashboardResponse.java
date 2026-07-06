package com.csjmu.arena.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminDashboardResponse {

    private long totalUsers;

    private long totalStudents;

    private long totalOrganizers;

    private long totalAdmins;

    private long totalEvents;

    private long pendingEvents;

    private long approvedEvents;

    private long rejectedEvents;

}