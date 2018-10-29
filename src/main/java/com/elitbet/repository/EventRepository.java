package com.elitbet.repository;

import com.elitbet.model.Event;
import com.elitbet.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
    Event getByDescriptionEquals(String description);

    List<Event> getAllByEventTypeEqualsAndStatusEqualsOrderByTime(EventType eventType, String status);
}