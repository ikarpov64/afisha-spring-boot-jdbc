package org.javaacademy.afisha.repository;

import org.javaacademy.afisha.entity.EventType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EventTypeRepository {

    public List<EventType> findAll() {
        return null;
    }

    public Optional<EventType> findById(Long id) {
        return Optional.empty();
    }

    public void save(EventType eventType) {

    }
}
