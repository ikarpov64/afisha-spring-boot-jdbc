package org.javaacademy.afisha.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.ReportDtoRs;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Репозиторий для выполнения запросов и получения отчетов по мероприятиям.
 * <p>
 * Этот компонент использует {@link JdbcTemplate} для выполнения SQL-запросов и получения данных.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class ReportRepository {
    private static String REPORT_QUERY = """
            SELECT ev.name as "event_name",
                   et.name as "event_type",
                   COALESCE(res.sum, 0) AS "sold_amount",
                   COALESCE(res.count, 0) AS "tickets_sold"
            FROM application.event ev
            LEFT JOIN application.event_type et ON ev.event_type_id = et.id
            LEFT JOIN (
                SELECT event_id,
                       SUM(price),
                       COUNT(*)
                FROM application.ticket
                WHERE is_sold = TRUE
                GROUP BY event_id
            ) res ON ev.id = res.event_id;
            """;
    private final JdbcTemplate jdbcTemplate;

    private static class ReportRowMapper implements RowMapper<ReportDtoRs> {
        @Override
        public ReportDtoRs mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReportDtoRs report = new ReportDtoRs();
            report.setEventName(rs.getString("event_name"));
            report.setEventType(rs.getString("event_type"));
            report.setTicketSold(rs.getLong("tickets_sold"));
            report.setSoldAmount(rs.getBigDecimal("sold_amount"));
            return report;
        }
    }

    /**
     * Получает отчет по мероприятиям.
     * <p>
     * Этот метод выполняет SQL-запрос, определенный в {@code REPORT_QUERY}, и возвращает список
     * объектов {@link ReportDtoRs}, содержащих данные о мероприятиях, типе мероприятия,
     * количестве проданных билетов и общей сумме продаж.
     * </p>
     *
     * @return список объектов {@link ReportDtoRs} с данными отчета.
     */
    public List<ReportDtoRs> getReport() {
        return jdbcTemplate.query(REPORT_QUERY, new ReportRowMapper());
    }
}
