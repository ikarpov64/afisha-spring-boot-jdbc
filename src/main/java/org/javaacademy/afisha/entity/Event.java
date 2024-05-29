package org.javaacademy.afisha.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Event {
    private Long id;
    private String name;
    private LocalDateTime eventDate;
    private EventType eventType;
    private Place place;
}
