package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.exception.PlaceNotFoundException;
import org.javaacademy.afisha.mapper.PlaceMapper;
import org.javaacademy.afisha.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    @SneakyThrows
    public List<PlaceDto> getAll() {
        return placeMapper.toPlacesDto(placeRepository.findAll());
    }

    @SneakyThrows
    public PlaceDto save(PlaceDto place) {
        Place savedPlace = placeRepository.save(placeMapper.toPlace(place));
        return placeMapper.toPlaceDto(savedPlace);
    }

    @SneakyThrows
    public PlaceDto getById(Long id) {
        return placeMapper.toPlaceDto(placeRepository.findById(id)
                .orElseThrow(() -> new PlaceNotFoundException("Place not found with id: " + id)));
    }
}
