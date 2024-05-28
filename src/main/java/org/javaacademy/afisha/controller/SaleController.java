package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.dto.TicketDtoRq;
import org.javaacademy.afisha.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PatchMapping("{id}")
    public ResponseEntity<TicketDto> saleTicket(@PathVariable Long id, @RequestBody TicketDtoRq clientEmail) {
        TicketDto ticketDto = saleService.saleTicket(id, clientEmail.getClientEmail());
        return ResponseEntity.ok(ticketDto);
    }
}
