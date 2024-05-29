package org.javaacademy.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.dto.TicketDtoRq;
import org.javaacademy.afisha.service.SaleService;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sale")
@RequiredArgsConstructor
@Tag(name = "Продажа билетов V1", description = "Методы для продажи билетов")
public class SaleController {
    private final SaleService saleService;

    @Operation(summary = "Продажа билета по ID мероприятия",
            description = "Продается билет на мероприятие по переданному ID мероприятия. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TicketDto.class))),
            @ApiResponse (responseCode = "404", description = "Нет мероприятия с таким ID",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse (responseCode = "406", description = "Нет свободных билетов для продажи",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("{id}")
    public ResponseEntity<TicketDto> saleTicket(@PathVariable @Parameter(description = "ID мероприятия")Long id,
            @RequestBody TicketDtoRq clientEmail) {
        TicketDto ticketDto = saleService.sellTicket(id, clientEmail.getClientEmail());
        return ResponseEntity.ok(ticketDto);
    }
}
