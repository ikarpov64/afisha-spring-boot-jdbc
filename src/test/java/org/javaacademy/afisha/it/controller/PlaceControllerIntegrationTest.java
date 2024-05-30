package org.javaacademy.afisha.it.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.repository.PlaceRepository;
import org.javaacademy.afisha.util.TestUtils;
import org.javaacademy.afisha.util.UrlConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
@AutoConfigureMockMvc
public class PlaceControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PlaceRepository placeRepository;

    @BeforeEach
    void clear() {
        placeRepository.deleteALL();
    }

    @Test
    @SneakyThrows
    void savePlace_AllOk_SaveAndReturnSaved() {
        ResultActions resultActions = mockMvc.perform(savePlace(TestUtils.getPlaceDto()));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(TestUtils.getPlace().getName()));

        Assertions.assertThat(placeRepository.findAll()).hasSize(1).flatExtracting(Place::getName)
                .containsExactly(TestUtils.getPlace().getName());
    }

    public MockHttpServletRequestBuilder savePlace(PlaceDto placeDto) throws JsonProcessingException {
        return MockMvcRequestBuilders.post(UrlConstants.PLACE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.jsonStringFromObject(placeDto));
    }
}
