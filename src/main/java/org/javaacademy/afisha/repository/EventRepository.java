package org.javaacademy.afisha.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.entity.Place;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

/**
 * Репозиторий для выполнения запросов и получения мероприятий.
 * <p>
 * Этот компонент использует {@link JdbcTemplate} для выполнения SQL-запросов и получения данных.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class EventRepository {
    private static String SELECT_QUERY = "SELECT * FROM application.event";
    private static String FIND_QUERY = "SELECT * FROM application.event WHERE ID=?";
    private static String INSERT_QUERY = "INSERT INTO application.event(name, event_date, event_type_id, place_id) "
            + "VALUES(?, ?, ?, ?)";
    private final JdbcTemplate jdbcTemplate;
    private final EventTypeRepository eventTypeRepository;
    private final PlaceRepository placeRepository;

    private class EventRowMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setId(rs.getLong("id"));
            event.setName(rs.getString("name"));
            event.setEventDate(rs.getTimestamp("event_date").toLocalDateTime());

            Long eventTypeId = rs.getLong("event_type_id");
            EventType eventType = eventTypeRepository.findById(eventTypeId).orElseThrow();
            event.setEventType(eventType);

            Long placeId = rs.getLong("place_id");
            Place place = placeRepository.findById(placeId).orElseThrow();
            event.setPlace(place);

            return event;
        }
    }

    public List<Event> findAll() {
        return jdbcTemplate.query(SELECT_QUERY, new EventRowMapper());
    }

    public Optional<Event> findById(Long id) {
        List<Event> events = jdbcTemplate.query(FIND_QUERY, new EventRowMapper(), id);
        return events.isEmpty() ? Optional.empty() : Optional.of(events.get(0));
    }

    public Event save(Event event) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
            ps.setString(1, event.getName());
            ps.setTimestamp(2, Timestamp.valueOf(event.getEventDate()));
            ps.setLong(3, event.getEventType().getId());
            ps.setLong(4, event.getPlace().getId());
            return ps;
        }, keyHolder);
        event.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return event;
    }
}
