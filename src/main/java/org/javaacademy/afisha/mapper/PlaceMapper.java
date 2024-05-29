package org.javaacademy.afisha.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.Place;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper implements RowMapper<Place> {

    public Place toPlace(PlaceDto placeDto) {
        Place place = new Place();
        place.setId(placeDto.getId());
        place.setName(placeDto.getName());
        place.setAddress(placeDto.getAddress());
        place.setCity(placeDto.getCity());
        return place;
    }

    public PlaceDto toPlaceDto(Place place) {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(place.getId());
        placeDto.setName(place.getName());
        placeDto.setAddress(place.getAddress());
        placeDto.setCity(place.getCity());
        return placeDto;
    }

    public List<PlaceDto> toPlacesDto(Collection<Place> places) {
        return places.stream()
                .map(this::toPlaceDto)
                .toList();
    }

    @Override
    public Place mapRow(ResultSet rs, int rowNum) throws SQLException {
        Place place = new Place();
        place.setId(rs.getLong("id"));
        place.setName(rs.getString("name"));
        place.setAddress(rs.getString("address"));
        place.setCity(rs.getString("city"));
        return place;
    }
}
