package org.javaacademy.afisha.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/event")
@RequiredArgsConstructor
@Tag(name = "Мероприятия V1", description = "Управление мероприятиями")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Получение всех мероприятий",
            description = "Возвращается список всех существующих мероприятий. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EventDto.class)))})
    @GetMapping
    public ResponseEntity<List<EventDto>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @Operation(summary = "Получение Мероприятия по ID",
            description = "Возвращается найденное Мероприятия по переданному ID. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketDto.class))),
            @ApiResponse (responseCode = "404", description = "Нет Мероприятия с таким ID",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable @Parameter(description = "ID мероприятия") Long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventDto> save(@RequestBody EventDto eventDto) {
        EventDto savedEvent = eventService.save(eventDto);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PostMapping("/many")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventDto> save(@RequestBody EventDtoRq eventDtoRq) {
        eventService.createEvents(eventDtoRq);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
