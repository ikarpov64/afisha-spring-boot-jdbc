package org.javaacademy.afisha.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/place")
@RequiredArgsConstructor
@Tag(name = "Места проведения V1", description = "Управление местами проведения мероприятий")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getAll() {
        return ResponseEntity.ok(placeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> findById(@PathVariable Long id) {
        PlaceDto place = placeService.getById(id);
        return new ResponseEntity<>(place, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlaceDto> save(@RequestBody PlaceDto placeDto) {
        PlaceDto savedPlace = placeService.save(placeDto);
        return new ResponseEntity<>(savedPlace, HttpStatus.CREATED);
    }
}
