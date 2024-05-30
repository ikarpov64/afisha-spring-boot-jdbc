package org.javaacademy.afisha.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.entity.Place;

public class TestUtils {

    private static final Long PLACE_ID = 1000L;

    public static Place getPlace() {
        Place place = new Place();
        place.setId(PLACE_ID);
        place.setName("theater");
        place.setCity("volsk");
        place.setAddress("central");
        return place;
    }

    public static PlaceDto getPlaceDto() {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(PLACE_ID);
        placeDto.setName("theater");
        placeDto.setCity("volsk");
        placeDto.setAddress("central");
        return placeDto;
    }

    public static String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        return objectMapper.writeValueAsString(object);
    }
}
