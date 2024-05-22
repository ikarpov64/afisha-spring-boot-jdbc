package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketRepository {
    private static String SELECT_QUERY = "SELECT * FROM ticket";
    private static String FIND_QUERY="SELECT * FROM ticket WHERE ID=?";
    private static String INSERT_QUERY = "INSERT INTO ticket(event_id, client_email, price) VALUES(?, ?, ?)";
    private static String UPDATE_QUERY = "UPDATE ticket SET is_sold=? WHERE ID=?";
    @Autowired
    private final DataSource dataSource;

    public List<Ticket> findAll() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
                while (resultSet.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setId(resultSet.getLong("id"));
                    tickets.add(ticket);
                }
                resultSet.close();
            }
            return tickets;
        }
    }

    public Optional<Ticket> findById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setId(resultSet.getLong("id"));
                    return Optional.of(ticket);
                } else throw new RuntimeException("Не найден билет по ID");
                }
            }
    }

    public void save(Ticket ticket) {
    }
}
