package org.javaacademy.afisha.it.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.util.TestUtils;
import org.javaacademy.afisha.util.UrlConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@RequiredArgsConstructor
public class PlaceControllerIntegrationTest {

    @BeforeEach
    public void init() {
        TestUtils.clearDb();
    }

    @Test
    @SneakyThrows
    @DisplayName("Тестирование Post запроса с проверкой Status code = 201")
    public void createPlaceReturnCreated() {
        PlaceDto savedPlaceDto = given()
                .body(TestUtils.jsonStringFromObject(TestUtils.getPlaceDto()))
                .contentType(ContentType.JSON)
                .post(UrlConstants.PLACE_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(PlaceDto.class);
        TestUtils.setPlaceDto(savedPlaceDto);
    }

    @Test
    @DisplayName("Тестирование GET запроса с проверкой status code = 200")
    public void getPlacesReturnsOk() {
        createPlaceReturnCreated();
        List<PlaceDto> places = given()
                .get(UrlConstants.PLACE_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", PlaceDto.class);

        assertFalse(places.isEmpty(), "В ответе должно быть как минимум 1 место проведения.");

        PlaceDto createdPlace = TestUtils.getPlaceDtoRs();
        assertTrue(places.stream().anyMatch(place -> place.getId().equals(createdPlace.getId()) &&
                        place.getName().equals(createdPlace.getName()) &&
                        place.getAddress().equals(createdPlace.getAddress()) &&
                        place.getCity().equals(createdPlace.getCity())),
                "Ответ должен содержать созданное место.");
    }

    @Test
    @DisplayName("Тестирование GET запроса с получением по id с проверкой status code = 200")
    public void getPlaceByIdReturnsOkWithExpectedId() {
        createPlaceReturnCreated();
        PlaceDto placeDto = TestUtils.getPlaceDtoRs();
        given()
                .pathParam("id", placeDto.getId())
                .get(UrlConstants.PLACE_URL_VAR)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("id", Matchers.is(placeDto.getId().intValue()))
                .body("name", Matchers.is(placeDto.getName()))
                .body("address", Matchers.is(placeDto.getAddress()))
                .body("city", Matchers.is(placeDto.getCity()));
    }
}
