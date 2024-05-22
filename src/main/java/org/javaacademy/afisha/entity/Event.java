package org.javaacademy.afisha.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    private Long id;
    private String name;
    private LocalDateTime eventDate;
    private EventType eventType;
    private Place place;
}
