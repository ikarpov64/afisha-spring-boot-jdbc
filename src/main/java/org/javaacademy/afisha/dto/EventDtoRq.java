package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDtoRq {
    private Long id;
    private String name;
    private LocalDateTime eventDate;
    private EventTypeDto eventType;
    private PlaceDto place;
    private BigDecimal price;
}
