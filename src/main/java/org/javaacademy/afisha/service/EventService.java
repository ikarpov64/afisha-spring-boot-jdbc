package org.javaacademy.afisha.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.*;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.exception.EventNotFoundException;
import org.javaacademy.afisha.exception.EventTypeNotFoundException;
import org.javaacademy.afisha.exception.PlaceNotFoundException;
import org.javaacademy.afisha.mapper.EventMapper;
import org.javaacademy.afisha.repository.EventRepository;
import org.javaacademy.afisha.repository.EventTypeRepository;
import org.javaacademy.afisha.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;


@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final PlaceRepository placeRepository;
    private final EventMapper eventMapper;
    private final TransactionTemplate transactionTemplate;
    private final TicketService ticketService;
    private final EventTypeService eventTypeService;

    public List<EventDto> getAll() {
        return eventMapper.toEventsDto(eventRepository.findAll());
    }

    public EventDto getById(Long id) {
        return eventMapper.toEventDto(eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with [id: %s]".formatted(id))));
    }

    public EventDto save(EventDto eventDto) {
        placeRepository.findById(eventDto.getPlace().getId()).orElseThrow(
                () -> new PlaceNotFoundException("Place not found with [id: %s]"
                        .formatted(eventDto.getPlace().getId())));
        eventTypeRepository.findById(eventDto.getEventType().getId()).orElseThrow(
                () -> new EventTypeNotFoundException("Event type not found with [id: %s]"
                        .formatted(eventDto.getEventType().getId())));

        Event savedEvent = eventRepository.save(eventMapper.toEvent(eventDto));
        return eventMapper.toEventDto(savedEvent);
    }

    /**
     * Создаёт события на основе переданных данных и соответствующих типов событий,
     * а также генерирует и сохраняет билеты для каждого события.
     * <p>
     * Метод выполняет следующие шаги в рамках транзакции:
     * 1. Получает все доступные типы событий.
     * 2. Для каждого типа события создаёт новое событие на основе входных данных.
     * 3. Сохраняет созданное событие.
     * 4. Генерирует и сохраняет билеты для события в зависимости от его типа.
     * </p>
     *
     * @param eventDtoRq объект {@link EventDtoRq}, содержащий данные для создания событий,
     *                   включая название, дату, место проведения и цену билета.
     */
    public void createEvents(EventDtoRq eventDtoRq) {
        List<EventTypeDto> allEventTypes = eventTypeService.getAll();
        transactionTemplate.executeWithoutResult((transactionStatus) -> {
            allEventTypes.forEach(eventTypeDto -> {
                EventDto eventDto = eventMapper.toEventDto(eventDtoRq);
                eventDto.setEventType(eventTypeDto);
                EventDto savedEvent = save(eventDto);
                ticketService.createTickets(savedEvent, eventDtoRq.getPrice(), eventTypeDto);
            });
        });
    }
}
