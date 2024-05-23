package org.javaacademy.afisha.mapper;

import org.javaacademy.afisha.dto.EventTypeDto;
import org.javaacademy.afisha.entity.EventType;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class EventTypeMapper {
    public EventType toEventType(EventTypeDto eventTypeDto) {
        return null;
    }

    public EventTypeDto toEventTypeDto(EventType eventType) {
        return null;
    }

    public List<EventTypeDto> toEventTypesDto(Collection<EventType> eventTypes) {
        return eventTypes.stream()
                .map(this::toEventTypeDto)
                .toList();
    }
}
