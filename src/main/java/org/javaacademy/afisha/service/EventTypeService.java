package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.dto.EventTypeDto;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.mapper.EventTypeMapper;
import org.javaacademy.afisha.repository.EventTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private final EventTypeMapper eventTypeMapper;

    @SneakyThrows
    public List<EventTypeDto> getAll() {
        return eventTypeMapper.toEventTypesDto(eventTypeRepository.findAll());
    }

    @SneakyThrows
    public EventTypeDto save(EventTypeDto eventType) {
        EventType savedEventType = eventTypeRepository.save(eventTypeMapper.toEventType(eventType));
        return eventTypeMapper.toEventTypeDto(savedEventType);
    }

    @SneakyThrows
    public EventTypeDto getById(Long id) {
        return eventTypeMapper.toEventTypeDto(eventTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + id)));
    }
}
