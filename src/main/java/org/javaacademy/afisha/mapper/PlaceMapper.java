package org.javaacademy.afisha.mapper;

import java.util.Collection;
import java.util.List;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.entity.Place;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper {

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
}
