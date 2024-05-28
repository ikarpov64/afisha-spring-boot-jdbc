package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.exception.TicketNotFoundException;
import org.javaacademy.afisha.mapper.TicketMapper;
import org.javaacademy.afisha.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
//    private final EventService eventService;

    public List<TicketDto> getAll() {
        return ticketMapper.toTicketsDto(ticketRepository.findAll());
    }

    public TicketDto getById(Long id) {
        return ticketMapper.toTicketDto(ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id)));
    }

    public List<TicketDto> getByEventId(Long id) {
        return ticketMapper.toTicketsDto(ticketRepository.findByEventId(id));
    }

    public TicketDto save(TicketDto ticketDto) {
        Ticket savedTicket = ticketRepository.save(ticketMapper.toTicket(ticketDto));
        return ticketMapper.toTicketDto(savedTicket);
    }

    public List<TicketDto> generateTickets(int ticketCount, EventDto eventDto, BigDecimal price) {
        return IntStream.range(0, ticketCount)
                .mapToObj(i -> {
                    TicketDto ticketDto = new TicketDto();
                    ticketDto.setEvent(eventDto);
                    ticketDto.setPrice(price);
                    return ticketDto;
                }).toList();
    }

}
