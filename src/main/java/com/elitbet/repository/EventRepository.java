package com.elitbet.repository;

import com.elitbet.model.Event;
import com.elitbet.model.EventStatus;
import com.elitbet.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
    Event getByFlashscoreIdEquals(String flashscoreId);

    List<Event> getAllByEventStatusEqualsAndEventTypeEqualsOrderByStartDateTime(
            EventStatus eventStatus, EventType eventType);

    List<Event> getAllByEventStatus_DescriptionNotLike(String description);
}