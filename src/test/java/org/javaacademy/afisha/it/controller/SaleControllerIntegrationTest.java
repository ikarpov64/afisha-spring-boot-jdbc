package org.javaacademy.afisha.it.controller;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@RequiredArgsConstructor
public class SaleControllerIntegrationTest {

    @BeforeEach
    public void init() {
        TestUtils.clearDb();
    }

    @Test
    @SneakyThrows
    @DisplayName("Тестирование PATCH запроса, с установкой email, и возвратом билета.")
    public void saleTicketReturnsOkAndTicketDto() {
        // Для продажи тикета нужно:
        // тикет -> для тикета событие -> для события место проведения.
        TestUtils.createPlace();
        TestUtils.createEvent();
        TicketDto ticketDto = given()
                .body(TestUtils.jsonStringFromObject(TestUtils.getTicketDto()))
                .contentType(ContentType.JSON)
                .patch(UrlConstants.SALE_URL, 1)
                .then().log().all()
                .statusCode(OK.value())
                .extract()
                .as(TicketDto.class);

        int i = 0;

    }
}
