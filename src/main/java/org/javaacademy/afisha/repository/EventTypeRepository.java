package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.entity.Place;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventTypeRepository {
    private static String SELECT_QUERY = "SELECT * FROM application.event_type";
    private static String FIND_QUERY = "SELECT * FROM application.event_type WHERE ID=?";
    private static String INSERT_QUERY = "INSERT INTO application.event_type(name) VALUES(?)";
    private static String UPDATE_QUERY = "UPDATE application.event_type SET is_sold=? WHERE ID=?";
    private final JdbcTemplate jdbcTemplate;
//    private final Connection connection;

    private static final RowMapper<EventType> PLACE_ROW_MAPPER = new RowMapper<EventType>() {
        @Override
        public EventType mapRow(ResultSet rs, int rowNum) throws SQLException {
            EventType eventType = new EventType();
            eventType.setId(rs.getLong("id"));
            eventType.setName(rs.getString("name"));
            return eventType;
        }
    };

    public List<EventType> findAll() {
        return jdbcTemplate.query(SELECT_QUERY, PLACE_ROW_MAPPER);
    }

    public Optional<EventType> findById(Long id) {
        List<EventType> eventTypes = jdbcTemplate.query(FIND_QUERY, PLACE_ROW_MAPPER, id);
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
