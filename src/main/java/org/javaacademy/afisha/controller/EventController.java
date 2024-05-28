package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDto>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable Long id) {
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
