package com.csjmu.arena.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizerDashboardResponse {

    private long totalEvents;

    private long pendingEvents;

    private long approvedEvents;

    private long rejectedEvents;

}