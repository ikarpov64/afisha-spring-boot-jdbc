package org.javaacademy.afisha.repository;

import org.javaacademy.afisha.entity.Place;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlaceRepository {

    public List<Place> findAll() {
        return null;
    }

    public Optional<Place> findById(Long id) {
        return Optional.empty();
    }

    public void save(Place place) {

    }
}
