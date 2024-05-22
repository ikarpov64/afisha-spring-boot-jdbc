package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.mapper.TicketMapper;
import org.javaacademy.afisha.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @SneakyThrows
    public List<TicketDto> findAll() {
        return ticketMapper.toTicketsDto(ticketRepository.findAll());
    }

}
