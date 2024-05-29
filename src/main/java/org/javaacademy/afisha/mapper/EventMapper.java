package org.javaacademy.afisha.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.repository.EventTypeRepository;
import org.javaacademy.afisha.repository.PlaceRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper implements RowMapper<Event> {
    private final EventTypeMapper eventTypeMapper;
    private final PlaceMapper placeMapper;
    private final EventTypeRepository eventTypeRepository;
    private final PlaceRepository placeRepository;

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

    public EventDto toEventDto(EventDtoRq eventDtoRq) {
        EventDto eventDto = new EventDto();
        eventDto.setId(eventDtoRq.getId());
        eventDto.setName(eventDtoRq.getName());
        eventDto.setEventDate(eventDtoRq.getEventDate());
        eventDto.setPlace(eventDtoRq.getPlace());
        return eventDto;
    }

    public List<EventDto> toEventsDto(Collection<Event> events) {
        return events.stream()
                .map(this::toEventDto)
                .toList();
    }

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();
        event.setId(rs.getLong("id"));
        event.setName(rs.getString("name"));
        event.setEventDate(rs.getTimestamp("event_date").toLocalDateTime());

        Long eventTypeId = rs.getLong("event_type_id");
        EventType eventType = eventTypeRepository.findById(eventTypeId).orElseThrow();
        event.setEventType(eventType);

        Long placeId = rs.getLong("place_id");
        Place place = placeRepository.findById(placeId).orElseThrow();
        event.setPlace(place);

        return event;
    }
}
