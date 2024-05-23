package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.entity.Place;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventTypeRepository {
    private static String SELECT_QUERY = "SELECT * FROM application.event_type";
    private static String FIND_QUERY = "SELECT * FROM application.event_type WHERE ID=?";
    private static String INSERT_QUERY = "INSERT INTO application.event_type(name) VALUES(?)";
    private static String UPDATE_QUERY = "UPDATE application.event_type SET is_sold=? WHERE ID=?";
    private final Connection connection;

    public List<EventType> findAll() throws SQLException {
        List<EventType> eventTypes = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            while (resultSet.next()) {
                EventType eventType = new EventType();
                eventType.setId(resultSet.getLong("id"));
                eventType.setName(resultSet.getString("name"));
                eventTypes.add(eventType);
            }
            return eventTypes;
        }
    }

    public Optional<EventType> findById(Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    EventType eventType = new EventType();
                    eventType.setId(resultSet.getLong("id"));
                    eventType.setName(resultSet.getString("name"));
                    return Optional.of(eventType);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    public EventType save(EventType eventType) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, eventType.getName());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    eventType.setId(generatedKeys.getLong(1));
                }
            }
        }
        return eventType;
    }
}
