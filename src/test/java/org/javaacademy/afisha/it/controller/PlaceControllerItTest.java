package org.javaacademy.afisha.it.controller;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;
import org.javaacademy.afisha.repository.PlaceRepository;
import org.javaacademy.afisha.util.TestUtils;
import org.javaacademy.afisha.util.UrlConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@RequiredArgsConstructor
public class PlaceControllerItTest {
    @Autowired
    private PlaceRepository placeRepository;

    @BeforeEach
    public void init() {
        placeRepository.deleteALL();
    }

    @Test
    @SneakyThrows
    @DisplayName("Тестирование Post запроса с проверкой Status code = 201, и проверкой полей.")
    public void createPlace() {
        RestAssured.given()
                .body(TestUtils.jsonStringFromObject(TestUtils.getPlaceDto()))
                .contentType(ContentType.JSON)
                .post(UrlConstants.PLACE_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .assertThat()
                .body("name", Matchers.is(TestUtils.getPlaceDto().getName()))
                .body("address", Matchers.is(TestUtils.getPlaceDto().getAddress()))
                .body("city", Matchers.is(TestUtils.getPlaceDto().getCity()));
    }

    @Test
    @DisplayName("Тестирование GET запроса с проверкой status code = 200")
    public void getPlace() {
        createPlace();
        RestAssured.given()
                .get(UrlConstants.PLACE_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Тестирование GET запроса с проверкой status code = 200")
    public void getPlaceById() {
        createPlace();
        RestAssured.given()
                .get(UrlConstants.PLACE_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
        System.out.println(":");
    }

}
