package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.config.TicketQuantitiesConfig;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.exception.TicketNotAvailableException;
import org.javaacademy.afisha.mapper.TicketMapper;
import org.javaacademy.afisha.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final TicketService ticketService;
    private final EventService eventService;
    private final TicketMapper ticketMapper;
    private final TicketQuantitiesConfig ticketQuantitiesConfig;

    public TicketDto sellTicket(Long eventId, String clientEmail) {
        EventDto event = eventService.getById(eventId);
        if (!ticketQuantitiesConfig.getQuantities().containsKey(event.getEventType().getName())) {
            TicketDto newTicket = new TicketDto();
            newTicket.setEvent(event);
            newTicket.setClientEmail(clientEmail);
            newTicket.setSold(true);
            return ticketService.save(newTicket);
        }
        Ticket ticket = ticketService.getByEventId(eventId);
        ticket.setClientEmail(clientEmail);
        ticket.setSold(true);
        if (ticketService.sellTicket(ticket) != 0) {
            return ticketMapper.toTicketDto(ticket);
        }
        throw new TicketNotAvailableException("No tickets for sale");
    }
}
