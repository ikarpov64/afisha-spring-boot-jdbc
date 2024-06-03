package org.javaacademy.afisha.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.EventTypeDto;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;

//@UtilityClass
@AutoConfigureMockMvc
@JdbcTest
//@RequiredArgsConstructor
public class TestUtils {
    private static final Long PLACE_ID = 1000L;
    private static PlaceDto placeDtoRs;
    private static EventDto eventDtoRs;

    @Autowired
    private static JdbcTemplate jdbcTemplate;

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

    public static TicketDto getTicketDto() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(PLACE_ID);
        ticketDto.setEvent(eventDtoRs);
        return ticketDto;
    }

    public static EventTypeDto getEventTypeDto() {
        return new EventTypeDto(2L, "cinema");
    }


    public static void createPlace() {
        PlaceDto placeDto = getPlaceDto();
        jdbcTemplate.update("INSERT INTO application.place (id, name, address, city) VALUES (?, ?, ?, ?)",
                placeDto.getId(), placeDto.getName(), placeDto.getAddress(), placeDto.getCity());
    }

    public static void createEvent() {
        String sql = "INSERT INTO application.event(name, event_date, event_type_id, place_id) "
                + "VALUES(?, ?, ?, ?)";
        PlaceDto placeDto = TestUtils.getPlaceDtoRs();
        EventDto eventDto = TestUtils.getEventDto();
        jdbcTemplate.update(sql, eventDto.getName(), eventDto.getEventDate(),
                eventDto.getEventType().getId(), placeDto.getId());
    }

    public static String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        return objectMapper.writeValueAsString(object);
    }

    public static void clearDb() {
        // Удаляем записи из зависимых таблиц
        jdbcTemplate.execute("DELETE FROM application.ticket");
        jdbcTemplate.execute("DELETE FROM application.event");
        jdbcTemplate.execute("DELETE FROM application.place");
    }

}
