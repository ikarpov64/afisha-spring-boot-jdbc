package org.javaacademy.afisha.it.controller;

import groovy.util.logging.Slf4j;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.javaacademy.afisha.util.UrlConstants.EVENT_URL;
import static org.javaacademy.afisha.util.UrlConstants.EVENT_URL_VAR;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@RequiredArgsConstructor
public class EventControllerIntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void init() {
        TestUtils.clearDb();
    }

    @Test
    @SneakyThrows
    @DisplayName("Тестирование POST запроса с проверкой Status code = 201")
    public void createEventReturnCreated() {
        TestUtils.createPlace();
        EventDto eventDto = given()
                .body(TestUtils.jsonStringFromObject(TestUtils.getEventDto()))
                .contentType(ContentType.JSON)
                .post(EVENT_URL)
                .then()
                .log().all()
                .statusCode(CREATED.value())
                .extract()
                .as(EventDto.class);
        TestUtils.setEventDto(eventDto);
    }

    @Test
    @DisplayName("Тестирование GET запроса с проверкой status code = 200")
    public void getEventReturnsOk() {
        createEventReturnCreated();
        List<EventDto> places = given()
                .get(EVENT_URL)
                .then()
                .log().all()
                .statusCode(OK.value())
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
    @DisplayName("Тестирование GET запроса с получением мероприятия по id с проверкой status code = 200")
    public void getEventByIdReturnsOkWithExpectedId() {
        createEventReturnCreated();
        EventDto eventDto = TestUtils.getEventDtoRs();
        given()
                .pathParam("id", eventDto.getId())
                .get(EVENT_URL_VAR)
                .then()
                .log().all()
                .statusCode(OK.value())
                .assertThat()
                .body("id", Matchers.is(eventDto.getId().intValue()))
                .body("name", Matchers.is(eventDto.getName()));
    }
}
