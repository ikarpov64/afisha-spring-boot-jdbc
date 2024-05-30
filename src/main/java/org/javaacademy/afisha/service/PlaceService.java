package org.javaacademy.afisha.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.exception.PlaceNotFoundException;
import org.javaacademy.afisha.mapper.PlaceMapper;
import org.javaacademy.afisha.repository.PlaceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    public List<PlaceDto> getAll() {
        return placeMapper.toPlacesDto(placeRepository.findAll());
    }

    public PlaceDto save(PlaceDto place) {
        Place savedPlace = placeRepository.save(placeMapper.toPlace(place));
        return placeMapper.toPlaceDto(savedPlace);
    }

    public PlaceDto getById(Long id) {
        return placeMapper.toPlaceDto(placeRepository.findById(id)
                .orElseThrow(() -> new PlaceNotFoundException("Place not found with [id: %s]".formatted(id))));
    }
}
