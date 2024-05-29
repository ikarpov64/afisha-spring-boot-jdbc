package org.javaacademy.afisha.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.ReportDtoRs;
import org.javaacademy.afisha.service.ReportService;
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

    @GetMapping
    public ResponseEntity<List<ReportDtoRs>> getReport() {
        return ResponseEntity.ok(reportService.report());
    }
}
