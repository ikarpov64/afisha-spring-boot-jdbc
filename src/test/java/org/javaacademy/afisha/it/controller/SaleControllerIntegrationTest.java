package org.javaacademy.afisha.it.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@RequiredArgsConstructor
public class SaleControllerIntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void init() {
        clearDb();
    }

    @Test
    @DisplayName("Тестирование PATCH запроса, с установкой email, и возвратом билета.")
    public void saleTicketReturnsOkAndTicketDto() {
        // Для продажи тикета нужно:
        // тикет -> для тикета событие -> для события место проведения.
        createPlaceForTicket();
//        createEvent();
//        createTicket();


    }

    private void clearDb() {
        // Удаляем записи из зависимых таблиц в правильном порядке
        jdbcTemplate.execute("DELETE FROM application.ticket");
        jdbcTemplate.execute("DELETE FROM application.event");
        jdbcTemplate.execute("DELETE FROM application.place");
    }

    private void createPlaceForTicket() {
        PlaceDto placeDto = TestUtils.getPlaceDto();
        jdbcTemplate.update("INSERT INTO application.place (id, name, address, city) VALUES (?, ?, ?, ?)",
                placeDto.getId(), placeDto.getName(), placeDto.getAddress(), placeDto.getCity());
    }

    private void createEventForTicket() {
        PlaceDto placeDto = TestUtils.getPlaceDtoRs();
        EventDto eventDto = TestUtils.getEventDto();
        jdbcTemplate.update("INSERT INTO application.event (id, name, address, city) VALUES (?, ?, ?, ?)",
                eventDto.getId(), eventDto.getName());
    }
}
