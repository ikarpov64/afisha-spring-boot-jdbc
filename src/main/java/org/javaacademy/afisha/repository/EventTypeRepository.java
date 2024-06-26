package org.javaacademy.afisha.repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.mapper.EventTypeMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

/**
 * Репозиторий для выполнения запросов и получения типов проведения мероприятий.
 * <p>
 * Этот компонент использует {@link JdbcTemplate} для выполнения SQL-запросов и получения данных.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class EventTypeRepository {
    private static String SELECT_QUERY = "SELECT * FROM application.event_type";
    private static String FIND_QUERY = "SELECT * FROM application.event_type WHERE ID=?";
    private static String INSERT_QUERY = "INSERT INTO application.event_type(name) VALUES(?)";
    private final EventTypeMapper eventTypeMapper;
    private final JdbcTemplate jdbcTemplate;

    public List<EventType> findAll() {
        return jdbcTemplate.query(SELECT_QUERY, eventTypeMapper);
    }

    public Optional<EventType> findById(Long id) {
        List<EventType> eventTypes = jdbcTemplate.query(FIND_QUERY, eventTypeMapper, id);
        return eventTypes.isEmpty() ? Optional.empty() : Optional.of(eventTypes.get(0));
    }

    public EventType save(EventType eventType) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
            ps.setString(1, eventType.getName());
            return ps;
        }, keyHolder);
        eventType.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return eventType;
    }
}
