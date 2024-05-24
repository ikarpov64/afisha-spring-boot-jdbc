package org.javaacademy.afisha.mapper;

import org.javaacademy.afisha.dto.EventTypeDto;
import org.javaacademy.afisha.entity.EventType;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class EventTypeMapper {
    public EventType toEventType(EventTypeDto eventTypeDto) {
        EventType eventType = new EventType();
        eventType.setId(eventTypeDto.getId());
        eventType.setName(eventTypeDto.getName());
        return eventType;
    }

    public EventTypeDto toEventTypeDto(EventType eventType) {
        EventTypeDto eventTypeDto = new EventTypeDto();
        eventTypeDto.setId(eventType.getId());
        eventTypeDto.setName(eventType.getName());
        return eventTypeDto;
    }

    public List<EventTypeDto> toEventTypesDto(Collection<EventType> eventTypes) {
        return eventTypes.stream()
                .map(this::toEventTypeDto)
                .toList();
    }
}
