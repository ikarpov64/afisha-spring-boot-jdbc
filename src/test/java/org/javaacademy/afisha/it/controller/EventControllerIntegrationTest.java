package org.javaacademy.afisha.it.controller;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.PlaceDto;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@RequiredArgsConstructor
public class EventControllerIntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void init() {
        clearDb();
    }

    @Test
    @SneakyThrows
    @DisplayName("Тестирование POST запроса с проверкой Status code = 201")
    public void createEventReturnCreated() {
        createPlaceForEvent();
        EventDto eventDto = RestAssured.given()
                .body(TestUtils.jsonStringFromObject(TestUtils.getEventDto()))
                .contentType(ContentType.JSON)
                .post(UrlConstants.EVENT_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(EventDto.class);
        TestUtils.setEventDto(eventDto);
    }

    @Test
    @DisplayName("Тестирование GET запроса с проверкой status code = 200")
    public void getEventReturnsOk() {
        createEventReturnCreated();
        List<EventDto> places = RestAssured.given()
                .get(UrlConstants.EVENT_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", EventDto.class);

        assertFalse(places.isEmpty(), "В ответе должно быть как минимум 1 мероприятие.");

        EventDto createdEvent = TestUtils.getEventDtoRs();
        assertTrue(places.stream().anyMatch(event -> event.getId().equals(createdEvent.getId()) &&
                        event.getName().equals(createdEvent.getName()) &&
                        event.getEventType().equals(createdEvent.getEventType()) &&
                        event.getPlace().equals(createdEvent.getPlace())),
                "Ответ должен содержать созданное мероприятие.");
    }

    @Test
    @DisplayName("Тестирование GET запроса с получением по id с проверкой status code = 200")
    public void getEventByIdReturnsOkWithExpectedId() {
        createEventReturnCreated();
        EventDto eventDto = TestUtils.getEventDtoRs();
        RestAssured.given()
                .pathParam("id", eventDto.getId())
                .get(UrlConstants.EVENT_URL_VAR)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("id", Matchers.is(eventDto.getId().intValue()))
                .body("name", Matchers.is(eventDto.getName()));
    }

    private void clearDb() {
        // Удаляем записи из зависимых таблиц в правильном порядке
        jdbcTemplate.execute("DELETE FROM application.ticket");
        jdbcTemplate.execute("DELETE FROM application.event");
        jdbcTemplate.execute("DELETE FROM application.place");
    }

    private void createPlaceForEvent() {
        PlaceDto placeDto = TestUtils.getPlaceDto();
        jdbcTemplate.update("INSERT INTO application.place (id, name, address, city) VALUES (?, ?, ?, ?)",
                placeDto.getId(), placeDto.getName(), placeDto.getAddress(), placeDto.getCity());
    }
}
