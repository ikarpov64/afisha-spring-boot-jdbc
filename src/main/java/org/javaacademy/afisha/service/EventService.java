package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.mapper.EventMapper;
import org.javaacademy.afisha.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public List<EventDto> getAll() {
        return eventMapper.toEventsDto(eventRepository.findAll());
    }

    public EventDto getById(Long id) {
        return eventMapper.toEventDto(eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id)));
    }

    public EventDto save(EventDto eventDto) {
        Event savedEvent = eventRepository.save(eventMapper.toEvent(eventDto));
        return eventMapper.toEventDto(savedEvent);
    }
}
