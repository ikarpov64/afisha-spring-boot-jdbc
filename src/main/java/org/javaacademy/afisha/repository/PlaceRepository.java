package org.javaacademy.afisha.repository;

import java.sql.*;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.mapper.PlaceMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * Репозиторий для выполнения запросов и получения мест проведения мероприятий.
 * <p>
 * Этот компонент использует {@link JdbcTemplate} для выполнения SQL-запросов и получения данных.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class PlaceRepository {
    private static String SELECT_QUERY = "SELECT * FROM application.place";
    private static String FIND_QUERY = "SELECT * FROM application.place WHERE ID=?";
    private static String INSERT_QUERY = "INSERT INTO application.place(name, address, city) VALUES(?, ?, ?)";
    private final PlaceMapper placeMapper;
    private final JdbcTemplate jdbcTemplate;

    public List<Place> findAll() {
        return jdbcTemplate.query(SELECT_QUERY, placeMapper);
    }

    public Optional<Place> findById(Long id) {
        List<Place> places = jdbcTemplate.query(FIND_QUERY, placeMapper, id);
        return places.isEmpty() ? Optional.empty() : Optional.of(places.get(0));
    }

    public Place save(Place place) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
            ps.setString(1, place.getName());
            ps.setString(2, place.getAddress());
            ps.setString(3, place.getCity());
            return ps;
        }, keyHolder);
        place.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return place;
    }
}
