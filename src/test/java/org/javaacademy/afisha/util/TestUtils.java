package org.javaacademy.afisha.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.EventTypeDto;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.entity.Place;

import java.time.LocalDateTime;

public class TestUtils {
    private static PlaceDto placeDtoRs;
    private static EventDto eventDtoRs;

    private static final Long PLACE_ID = 1000L;


    public static void setPlaceDto(PlaceDto placeDto) {
        TestUtils.placeDtoRs = placeDto;
    }

    public static void setEventDto(EventDto eventDto) {
        TestUtils.eventDtoRs = eventDto;
    }

    public static PlaceDto getPlaceDtoRs() {
        return placeDtoRs;
    }

    public static EventDto getEventDtoRs() {
        return eventDtoRs;
    }

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

    public static EventDto getEventDto() {
        EventDto eventDto = new EventDto();
        eventDto.setEventType(getEventTypeDto());
        eventDto.setPlace(getPlaceDto());
        eventDto.setEventDate(LocalDateTime.now());
        eventDto.setName("various artist");
        return eventDto;
    }

    public static EventTypeDto getEventTypeDto() {
        return new EventTypeDto(2L, "cinema");
    }

    public static String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        return objectMapper.writeValueAsString(object);
    }



}
