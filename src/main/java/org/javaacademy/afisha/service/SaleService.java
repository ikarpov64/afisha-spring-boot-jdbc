package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.mapper.TicketMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SaleService {
    private final TicketService ticketService;
    private final EventService eventService;
    private final TicketMapper ticketMapper;

    public TicketDto saleTicket(Long eventId, String clientEmail) {
        EventDto event = eventService.getById(eventId);
        if (event.getEventType().getName().equals("museum")) {
//         TODO: 28.05.2024 Создать билет в музей. Ретурн.
        }
        Ticket ticket = ticketService.getByEventId(eventId);
        ticket.setClientEmail(clientEmail);
        ticket.setSold(true);
        if (ticketService.sellTicket(ticket) != 0) {
            return ticketMapper.toTicketDto(ticket);
        }
        return null;
    }
}
