package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.ReportDtoRs;
import org.javaacademy.afisha.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    /**
     * Генерирует отчет по мероприятиям с деталями о количестве проданных билетов и общей сумме продаж.
     * <p>
     * Этот метод извлекает список объектов {@link ReportDtoRs}, каждый из которых представляет мероприятие со
     * следующими деталями:
     * <ul>
     *   <li>Название мероприятия</li>
     *   <li>Тип мероприятия</li>
     *   <li>Количество проданных билетов</li>
     *   <li>Общая сумма продаж</li>
     * </ul>
     * Данные извлекаются из {@link ReportRepository}.
     * </p>
     *
     * @return список объектов {@link ReportDtoRs}, содержащих данные отчета по мероприятиям.
     */
    public List<ReportDtoRs> report() {
        return reportRepository.getReport();
    }
}
