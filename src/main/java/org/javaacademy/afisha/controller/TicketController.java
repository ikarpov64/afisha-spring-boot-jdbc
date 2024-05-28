package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.service.SaleService;
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
    private final SaleService saleService;

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

    @PatchMapping("{id}")
    public ResponseEntity<Object> buyTicket(@PathVariable Long id, @RequestBody String clientEmail) {
        saleService.saleTicket(id, clientEmail);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
