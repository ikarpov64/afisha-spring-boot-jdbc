package org.javaacademy.afisha.repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.mapper.TicketMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

/**
 * Репозиторий для выполнения запросов и получения билетов на мероприятия.
 * <p>
 * Этот компонент использует {@link JdbcTemplate} для выполнения SQL-запросов и получения данных.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class TicketRepository {
    private static String SELECT_QUERY = "SELECT * FROM ticket";
    private static String FIND_QUERY = "SELECT * FROM ticket WHERE ID=?";
    private static String FIND_QUERY_BY_EVENT_ID = "SELECT * FROM ticket WHERE event_id=? and is_sold=? LIMIT 1";
    private static String INSERT_QUERY = "INSERT INTO ticket(event_id, client_email, price) VALUES(?, ?, ?)";
    private static String UPDATE_QUERY = "UPDATE ticket SET is_sold=?, client_email=? WHERE id=?";
    private final TicketMapper ticketMapper;
    private final JdbcTemplate jdbcTemplate;

    public List<Ticket> findAll() {
        return jdbcTemplate.query(SELECT_QUERY, ticketMapper);
    }

    public Optional<Ticket> findById(Long id) {
        List<Ticket> tickets = jdbcTemplate.query(FIND_QUERY, ticketMapper, id);
        return tickets.isEmpty() ? Optional.empty() : Optional.of(tickets.get(0));
    }

    public Optional<Ticket> findByEventId(Long id) {
        List<Ticket> tickets = jdbcTemplate.query(FIND_QUERY_BY_EVENT_ID, ticketMapper, id, false);
        return tickets.isEmpty() ? Optional.empty() : Optional.of(tickets.get(0));
    }

    public int sell(TicketDto ticketDto) {
        return jdbcTemplate.update(UPDATE_QUERY, true, ticketDto.getClientEmail(), ticketDto.getId());
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
