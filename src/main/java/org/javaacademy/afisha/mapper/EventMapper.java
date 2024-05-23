package org.javaacademy.afisha.mapper;

import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.entity.Event;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class EventMapper {

    public Event toEvent(EventDto eventDto) {
        return null;
    }

    public EventDto toEventDto(Event event) {
        return null;
    }

    public List<EventDto> toEventsDto(Collection<Event> events) {
        return events.stream()
                .map(this::toEventDto)
                .toList();
    }
}
