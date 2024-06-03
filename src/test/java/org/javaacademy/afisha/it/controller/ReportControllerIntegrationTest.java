package org.javaacademy.afisha.it.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.javaacademy.afisha.util.UrlConstants.REPORT_URL;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@RequiredArgsConstructor
public class ReportControllerIntegrationTest {

    @Test
    @DisplayName("Тестирование GET запроса с проверкой status code = 200")
    public void getReportReturnsOk() {

        given()
                .get(REPORT_URL)
                .then().log().all()
                .statusCode(OK.value());
    }
}
