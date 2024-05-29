package org.javaacademy.afisha.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.repository.EventRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketMapper implements RowMapper<Ticket> {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

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

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getLong("id"));
        ticket.setPrice(rs.getBigDecimal("price"));
        ticket.setClientEmail(rs.getString("client_email"));

        Long evenId = rs.getLong("event_id");
        Event event = eventRepository.findById(evenId).orElseThrow();
        ticket.setEvent(event);

        ticket.setSold(rs.getBoolean("is_sold"));

        return ticket;
    }
}
