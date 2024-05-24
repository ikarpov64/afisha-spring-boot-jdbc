package org.javaacademy.afisha.mapper;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.entity.Event;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final EventTypeMapper eventTypeMapper;
    private final PlaceMapper placeMapper;

    public Event toEvent(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setEventDate(eventDto.getEventDate());
        event.setEventType(eventTypeMapper.toEventType(eventDto.getEventType()));
        event.setPlace(placeMapper.toPlace(eventDto.getPlace()));
        return event;
    }

    public EventDto toEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setEventDate(event.getEventDate());
        eventDto.setEventType(eventTypeMapper.toEventTypeDto(event.getEventType()));
        eventDto.setPlace(placeMapper.toPlaceDto(event.getPlace()));
        return eventDto;
    }

    public List<EventDto> toEventsDto(Collection<Event> events) {
        return events.stream()
                .map(this::toEventDto)
                .toList();
    }
}
