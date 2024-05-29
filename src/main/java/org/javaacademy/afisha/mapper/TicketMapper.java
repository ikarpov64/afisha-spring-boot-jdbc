package org.javaacademy.afisha.mapper;

import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketMapper {
    private final EventMapper eventMapper;

    public TicketDto toTicketDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setEvent(eventMapper.toEventDto(ticket.getEvent()));
        ticketDto.setClientEmail(ticket.getClientEmail());
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setSold(ticket.isSold());
        return ticketDto;
    }

    public Ticket toTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDto.getId());
        ticket.setEvent(eventMapper.toEvent(ticketDto.getEvent()));
        ticket.setClientEmail(ticketDto.getClientEmail());
        ticket.setPrice(ticketDto.getPrice());
        ticket.setSold(ticketDto.isSold());
        return ticket;
    }

    public List<TicketDto> toTicketsDto(Collection<Ticket> tickets) {
        return tickets.stream()
                .map(this::toTicketDto)
                .toList();
    }
}
