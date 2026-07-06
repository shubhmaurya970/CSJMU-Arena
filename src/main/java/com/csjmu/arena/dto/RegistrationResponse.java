package com.csjmu.arena.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegistrationResponse {

    private Long registrationId;

    private String eventTitle;

    private String studentName;

    private String message;
}