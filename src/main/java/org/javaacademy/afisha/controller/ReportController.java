package org.javaacademy.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.ReportDtoRs;
import org.javaacademy.afisha.service.ReportService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
@Tag(name = "Отчет V1", description = "Методы получения отчета по проданным билетам")
public class ReportController {
    private final ReportService reportService;

    @Operation(summary = "Отчет по проданным билетам",
            description = "Отчет в разрезе: имя мероприятия, тип мероприятия, количество проданных билетов, "
                    + "сумма проданных билетов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReportDtoRs.class)))})
    @GetMapping
    public ResponseEntity<List<ReportDtoRs>> getReport() {
        return ResponseEntity.ok(reportService.report());
    }
}
