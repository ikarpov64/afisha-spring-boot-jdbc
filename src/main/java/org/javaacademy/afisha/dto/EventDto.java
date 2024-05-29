package org.javaacademy.afisha.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
