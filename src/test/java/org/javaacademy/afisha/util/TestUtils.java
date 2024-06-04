package org.javaacademy.afisha.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import org.javaacademy.afisha.dto.*;
import org.javaacademy.afisha.entity.Place;
import java.time.LocalDateTime;

@UtilityClass
public class TestUtils {
    private static final Long DB_ENTITY_ID = 1000L;
    private static final String PLACE_NAME = "theater";
    private static final String PLACE_CITY = "volsk";
    private static final String PLACE_ADDRESS = "central";
    private static final String TICKET_EMAIL = "test@email.com";
    private static final String EVENT_NAME = "various artist";

    private static PlaceDto placeDtoRs;
    private static EventDto eventDtoRs;

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

//    public static Place getPlace() {
//        Place place = new Place();
//        place.setId(DB_ENTITY_ID);
//        place.setName(PLACE_NAME);
//        place.setCity(PLACE_CITY);
//        place.setAddress(PLACE_ADDRESS);
//        return place;
//    }

    public static PlaceDto getPlaceDto() {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(DB_ENTITY_ID);
        placeDto.setName(PLACE_NAME);
        placeDto.setCity(PLACE_CITY);
        placeDto.setAddress(PLACE_ADDRESS);
        return placeDto;
    }

    public static EventDto getEventDto() {
        EventDto eventDto = new EventDto();
        eventDto.setId(DB_ENTITY_ID);
        eventDto.setEventType(getEventTypeDto());
        eventDto.setPlace(getPlaceDto());
        eventDto.setEventDate(LocalDateTime.now());
        eventDto.setName(EVENT_NAME);
        return eventDto;
    }

//    public static TicketDto getTicketDto() {
//        TicketDto ticketDto = new TicketDto();
//        ticketDto.setId(DB_ENTITY_ID);
//        ticketDto.setEvent(eventDtoRs);
//        return ticketDto;
//    }

    public static TicketDtoRq getTicketDtoRq() {
        TicketDtoRq ticketDto = new TicketDtoRq();
        ticketDto.setClientEmail(TICKET_EMAIL);
        return ticketDto;
    }

    public static EventTypeDto getEventTypeDto() {
        return new EventTypeDto(2L, "cinema");
    }

    public static String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        return objectMapper.writeValueAsString(object);
    }
}
