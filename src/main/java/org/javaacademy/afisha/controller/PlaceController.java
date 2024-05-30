package org.javaacademy.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/place")
@RequiredArgsConstructor
@Tag(name = "Место проведения V1", description = "Управление местами проведения мероприятий")
public class PlaceController {
    private final PlaceService placeService;

    @Operation(summary = "Получение всех мест проведения мероприятий",
            description = "Возвращается список всех существующих мест проведения мероприятий. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PlaceDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<PlaceDto>> getAll() {
        return ResponseEntity.ok(placeService.getAll());
    }

    @Operation(summary = "Получение места проведения мероприятия по ID",
            description = "Возвращается найденное место проведения мероприятия по указанному ID. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PlaceDto.class))),
            @ApiResponse(responseCode = "404", description = "Нет места проведения мероприятия с таким ID",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> findById(@PathVariable @Parameter(description = "ID места") Long id) {
        PlaceDto place = placeService.getById(id);
        return new ResponseEntity<>(place, HttpStatus.OK);
    }

    @Operation(summary = "Создание нового места проведения мероприятия",
            description = "Создается единичное место проведения мероприятия по переданному телу json.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "OK: Место проведения мероприятия сохранено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PlaceDto.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlaceDto> save(@RequestBody PlaceDto placeDto) {
        PlaceDto savedPlace = placeService.save(placeDto);
        return new ResponseEntity<>(savedPlace, HttpStatus.CREATED);
    }
}
