package org.javaacademy.afisha.repository;

import org.javaacademy.afisha.entity.Event;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EventRepository {

    public List<Event> findAll() {
        return null;
    }

    public Optional<Event> findById(Long id) {
        return Optional.empty();
    }

    public void save(Event event) {

    }
}
