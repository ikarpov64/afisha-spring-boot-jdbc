package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.entity.Ticket;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlaceRepository {
    private static String SELECT_QUERY = "SELECT * FROM application.place";
    private static String FIND_QUERY = "SELECT * FROM application.place WHERE ID=?";
    private static String INSERT_QUERY = "INSERT INTO application.place(name, address, city) VALUES(?, ?, ?)";
    private static String UPDATE_QUERY = "UPDATE application.place SET is_sold=? WHERE ID=?";
    private final Connection connection;

    public List<Place> findAll() throws SQLException {
        List<Place> places = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            while (resultSet.next()) {
                Place place = new Place();
                place.setId(resultSet.getLong("id"));
                place.setName(resultSet.getString("name"));
                place.setAddress(resultSet.getString("address"));
                place.setCity(resultSet.getString("city"));
                places.add(place);
            }
            return places;
        }
    }

    public Optional<Place> findById(Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Place place = new Place();
                    place.setId(resultSet.getLong("id"));
                    place.setName(resultSet.getString("name"));
                    place.setAddress(resultSet.getString("address"));
                    place.setCity(resultSet.getString("city"));
                    return Optional.of(place);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    public Place save(Place place) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, place.getName());
            preparedStatement.setString(2, place.getAddress());
            preparedStatement.setString(3, place.getCity());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    place.setId(generatedKeys.getLong(1));
                }
            }
        }
        return place;
    }
}
