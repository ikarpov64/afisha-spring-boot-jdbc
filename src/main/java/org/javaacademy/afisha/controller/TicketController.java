package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAll() {
        return ResponseEntity.ok(ticketService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TicketDto> save(@RequestBody TicketDto ticketDto) {
        TicketDto savedTicket = ticketService.save(ticketDto);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }
}
