package org.javaacademy.afisha.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventTypeDto;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.exception.EventTypeNotFoundException;
import org.javaacademy.afisha.mapper.EventTypeMapper;
import org.javaacademy.afisha.repository.EventTypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private final EventTypeMapper eventTypeMapper;

    public List<EventTypeDto> getAll() {
        return eventTypeMapper.toEventTypesDto(eventTypeRepository.findAll());
    }

    public EventTypeDto save(EventTypeDto eventType) {
        EventType savedEventType = eventTypeRepository.save(eventTypeMapper.toEventType(eventType));
        return eventTypeMapper.toEventTypeDto(savedEventType);
    }

    public EventTypeDto getById(Long id) {
        return eventTypeMapper.toEventTypeDto(eventTypeRepository.findById(id)
                .orElseThrow(() -> new EventTypeNotFoundException("Event type not found with id: " + id)));
    }
}
