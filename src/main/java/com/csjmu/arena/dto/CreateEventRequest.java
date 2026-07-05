package com.csjmu.arena.dto;

import com.csjmu.arena.entity.EventCategory;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEventRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Venue is required")
    private String venue;

    @NotNull(message = "Event date is required")
    private LocalDate eventDate;

    @NotNull(message = "Registration deadline is required")
    private LocalDate registrationDeadline;

    @NotNull(message = "Category is required")
    private EventCategory category;

    @NotNull(message = "Maximum participants is required")
    @Min(value = 1, message = "Maximum participants must be at least 1")
    private Integer maxParticipants;

    @Builder.Default
    private Double registrationFee = 0.0;

    private String imageUrl;
}