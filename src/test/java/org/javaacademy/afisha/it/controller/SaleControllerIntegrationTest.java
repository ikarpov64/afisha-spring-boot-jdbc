package org.javaacademy.afisha.it.controller;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.util.TestUtils;
import org.javaacademy.afisha.util.UrlConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@RequiredArgsConstructor
public class SaleControllerIntegrationTest {

    private static final BigDecimal TICKET_PRICE = BigDecimal.valueOf(1000);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void init() {
        clearDb();
    }

    @Test
    @SneakyThrows
    @DisplayName("Тестирование PATCH запроса, с установкой email, и возвратом проданного билета.")
    public void saleTicketReturnsOkAndTicketDto() {
        fillDependentTables();
        given()
                .body(TestUtils.jsonStringFromObject(TestUtils.getTicketDtoRq()))
                .contentType(ContentType.JSON)
                .patch(UrlConstants.SALE_URL, TestUtils.getEventDto().getId())
                .then().log().all()
                .statusCode(OK.value())
                .assertThat()
                .body("sold", Matchers.is(true))
                .body("clientEmail", Matchers.is(TestUtils.getTicketDtoRq().getClientEmail()));
    }

    @Test
    @DisplayName("Тестирование PATCH запроса, с возвратом 404, не найдено событие")
    @SneakyThrows
    public void saleTicketReturnsNotFoundEvent() {
        fillDependentTables();
        given()
                .body(TestUtils.jsonStringFromObject(TestUtils.getTicketDtoRq()))
                .contentType(ContentType.JSON)
                .patch(UrlConstants.SALE_URL, 1)
                .then().log().all()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    @DisplayName("Тестирование PATCH запроса, с возвратом 406, нет билета")
    @SneakyThrows
    public void saleTicketReturnsNotFoundTicket() {
        fillDependentTables();
        given()
                .body(TestUtils.jsonStringFromObject(TestUtils.getTicketDtoRq()))
                .contentType(ContentType.JSON)
                .patch(UrlConstants.SALE_URL, TestUtils.getEventDto().getId())
                .then().log().all()
                .statusCode(OK.value());

        given()
                .body(TestUtils.jsonStringFromObject(TestUtils.getTicketDtoRq()))
                .contentType(ContentType.JSON)
                .patch(UrlConstants.SALE_URL, TestUtils.getEventDto().getId())
                .then().log().all()
                .statusCode(NOT_ACCEPTABLE.value());
    }

    private void fillDependentTables() {
        createPlace();
        createEvent();
        createTicket();
    }

    private void createTicket() {
        String sql = "INSERT INTO application.ticket (event_id, price) VALUES (?, ?)";
        jdbcTemplate.update(sql, TestUtils.getEventDto().getId(), TICKET_PRICE);
    }

    private void createPlace() {
        PlaceDto placeDto = TestUtils.getPlaceDto();
        jdbcTemplate.update("INSERT INTO application.place (id, name, address, city) VALUES (?, ?, ?, ?)",
                placeDto.getId(), placeDto.getName(), placeDto.getAddress(), placeDto.getCity());
    }

    private  void createEvent() {
        String sql = "INSERT INTO application.event(id, name, event_date, event_type_id, place_id) "
                + "VALUES(?, ?, ?, ?, ?)";
        PlaceDto placeDto = TestUtils.getPlaceDto();
        EventDto eventDto = TestUtils.getEventDto();
        jdbcTemplate.update(sql, eventDto.getId(), eventDto.getName(), eventDto.getEventDate(),
                eventDto.getEventType().getId(), placeDto.getId());
    }

    private void clearDb() {
        // Удаляем записи из зависимых таблиц
        jdbcTemplate.execute("DELETE FROM application.ticket");
        jdbcTemplate.execute("DELETE FROM application.event");
        jdbcTemplate.execute("DELETE FROM application.place");
    }
}
