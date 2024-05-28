package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.TicketDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final TicketService ticketService;
    private final EventService eventService;

    public void saleTicket(Long eventId, String clientEmail) {
        EventDto event = eventService.getById(eventId);
        if (event.getEventType().getName().equals("museum")) {
//         TODO: 28.05.2024 Создать билет в музей. Ретурн.
        }
        // TODO: 28.05.2024 Найти все билеты по эвент айди.
        List<TicketDto> byEventId = ticketService.getByEventId(eventId);
//        String eventType = ticketDtoRq.getEventType();
//        eventTypeService.
    }
}
