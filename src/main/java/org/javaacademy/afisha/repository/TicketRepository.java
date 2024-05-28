package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketRepository {
    private static String SELECT_QUERY = "SELECT * FROM ticket";
    private static String FIND_QUERY = "SELECT * FROM ticket WHERE ID=?";
    private static String FIND_QUERY_BY_EVENT_ID = "SELECT * FROM ticket WHERE event_id=? and is_sold=?";
    private static String INSERT_QUERY = "INSERT INTO ticket(event_id, client_email, price) VALUES(?, ?, ?)";
    private static String UPDATE_QUERY = "UPDATE ticket SET is_sold=? WHERE ID=?";
    private final EventRepository eventRepository;
    private final JdbcTemplate jdbcTemplate;
    private class TicektRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(rs.getLong("id"));
            ticket.setPrice(rs.getBigDecimal("price"));
            ticket.setClientEmail(rs.getString("client_email"));

            Long evenId = rs.getLong("event_id");
            Event event = eventRepository.findById(evenId).orElseThrow();
            ticket.setEvent(event);

            return ticket;
        }
    };

    public List<Ticket> findAll() {
        return jdbcTemplate.query(SELECT_QUERY, new TicektRowMapper());
    }

    public Optional<Ticket> findById(Long id) {
        List<Ticket> tickets = jdbcTemplate.query(FIND_QUERY, new TicektRowMapper(), id);
        return tickets.isEmpty() ? Optional.empty() : Optional.of(tickets.get(0));
    }

    public List<Ticket> findByEventId(Long id) {
        return jdbcTemplate.query(FIND_QUERY_BY_EVENT_ID, new TicektRowMapper(), id, false);
    }

    public Ticket save(Ticket ticket) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
            ps.setLong(1, ticket.getEvent().getId());
            ps.setString(2, ticket.getClientEmail());
            ps.setBigDecimal(3, ticket.getPrice());
            return ps;
        }, keyHolder);
        ticket.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return ticket;
    }
}
