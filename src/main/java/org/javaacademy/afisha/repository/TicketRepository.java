package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketRepository {
    private static String FIND_QUERY="SELECT * FROM ticket WHERE ID=?";
    private static String INSERT_QUERY = "INSERT INTO ticket(event_id, client_email, price) VALUES(?, ?, ?)";
    private static String UPDATE_QUERY = "UPDATE ticket SET is_sold=? WHERE ID=?";
    private static String SELECT_QUERY = "SELECT * FROM ticket";
    @Autowired
    DataSource dataSource;

    public List<Ticket> findAll() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(FIND_QUERY);
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

    public Optional<Ticket> findById(Long id) {
        return Optional.empty();
    }

    public void save(Ticket ticket) {

    }
}
