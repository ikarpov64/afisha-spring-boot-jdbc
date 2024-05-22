package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;
    private String name;
    private LocalDateTime eventDate;
    private EventTypeDto eventType;
    private PlaceDto place;
}
