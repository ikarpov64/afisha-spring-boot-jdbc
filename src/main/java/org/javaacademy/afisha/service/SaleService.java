package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.config.TicketQuantitiesConfig;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.ReportDtoRs;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Ticket;
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

    /**
     * Генерирует отчет по мероприятиям с деталями о количестве проданных билетов и общей сумме продаж.
     * <p>
     * Этот метод извлекает список объектов {@link TicketDto}, каждый из которых представляет мероприятие с
     * следующими деталями:
     * <ul>
     *   <li>Название мероприятия</li>
     *   <li>Тип мероприятия</li>
     *   <li>Количество проданных билетов</li>
     *   <li>Общая сумма продаж</li>
     * </ul>
     * Данные извлекаются из {@link ReportRepository}.
     * </p>
     *
     * @return объект {@link TicketDto}, содержащий данные о проданном билете.
     */
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
        return null;
    }
}
