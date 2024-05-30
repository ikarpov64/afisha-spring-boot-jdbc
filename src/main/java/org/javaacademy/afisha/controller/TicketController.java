package org.javaacademy.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ticket")
@RequiredArgsConstructor
@Tag(name = "Билеты V1", description = "Управление билетами для мероприятий")
public class TicketController {
    private final TicketService ticketService;

    @Operation(summary = "Получение всех билетов",
            description = "Возвращается список всех существующих билетов. И проданных и не проданных.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<TicketDto>> getAll() {
        return ResponseEntity.ok(ticketService.getAll());
    }

    @Operation(summary = "Получение билета по ID",
            description = "Возвращается найденный билет по указанному ID. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketDto.class))),
            @ApiResponse (responseCode = "404", description = "Нет билета с таким ID",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getById(id));
    }

    @Operation(summary = "Создание нового единичного билета",
            description = "Создается единичный билет по переданному телу json.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "OK: Билет сохранен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketDto.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TicketDto> save(@RequestBody TicketDto ticketDto) {
        TicketDto savedTicket = ticketService.save(ticketDto);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }
}
