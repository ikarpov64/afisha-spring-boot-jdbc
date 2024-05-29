package org.javaacademy.afisha.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.javaacademy.afisha.dto.EventTypeDto;
import org.javaacademy.afisha.entity.EventType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EventTypeMapper implements RowMapper<EventType> {
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

    @Override
    public EventType mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventType eventType = new EventType();
        eventType.setId(rs.getLong("id"));
        eventType.setName(rs.getString("name"));
        return eventType;
    }
}
