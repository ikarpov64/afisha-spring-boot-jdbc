package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/place")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getAll() {
        return ResponseEntity.ok(placeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(placeService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlaceDto> save(@RequestBody PlaceDto placeDto) {
        PlaceDto savedPlace = placeService.save(placeDto);
        return new ResponseEntity<>(savedPlace, HttpStatus.CREATED);
    }
}