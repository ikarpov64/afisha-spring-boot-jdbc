package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.config.TicketQuantitiesConfig;
import org.javaacademy.afisha.dto.*;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.exception.EventNotFoundException;
import org.javaacademy.afisha.mapper.EventMapper;
import org.javaacademy.afisha.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final TransactionTemplate transactionTemplate;
    private final TicketService ticketService;
    private final EventTypeService eventTypeService;
    private final TicketQuantitiesConfig ticketQuantitiesConfig;

    public List<EventDto> getAll() {
        return eventMapper.toEventsDto(eventRepository.findAll());
    }

    public EventDto getById(Long id) {
        return eventMapper.toEventDto(eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id)));
    }

    public EventDto save(EventDto eventDto) {
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
        Map<String, Integer> ticketQuantities = ticketQuantitiesConfig.getQuantities();

        transactionTemplate.executeWithoutResult((transactionStatus) -> {
            List<EventTypeDto> allEventTypes = eventTypeService.getAll();

            allEventTypes.forEach(eventTypeDto -> {
                EventDto eventDto = eventMapper.toEventDto(eventDtoRq);
                eventDto.setEventType(eventTypeDto);
                EventDto savedEvent = save(eventDto);

                int ticketQty = ticketQuantities.getOrDefault(eventTypeDto.getName(), 0);
                if (ticketQty > 0) {
                    List<TicketDto> ticketsDto = ticketService.generateTickets(ticketQty, savedEvent, eventDtoRq.getPrice());
                    ticketsDto.forEach(ticketService::save);
                }
            });
        });
    }
}
