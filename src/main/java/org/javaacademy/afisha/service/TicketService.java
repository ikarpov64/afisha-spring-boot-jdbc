package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.exception.TicketNotFoundException;
import org.javaacademy.afisha.mapper.TicketMapper;
import org.javaacademy.afisha.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public List<TicketDto> getAll() {
        return ticketMapper.toTicketsDto(ticketRepository.findAll());
    }

    public TicketDto getById(Long id) {
        return ticketMapper.toTicketDto(ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id)));
    }

    public TicketDto save(TicketDto ticketDto) {
        Ticket savedTicket = ticketRepository.save(ticketMapper.toTicket(ticketDto));
        return ticketMapper.toTicketDto(savedTicket);
    }

}
