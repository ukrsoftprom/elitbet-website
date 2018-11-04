package com.elitbet.repository;

import com.elitbet.model.Event;
import com.elitbet.model.EventStatus;
import com.elitbet.model.EventType;
import com.elitbet.model.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
    Event getByFlashscoreIdEquals(String flashscoreId);

    List<Event> findAllByEventStatusAndEventType(
            EventStatus eventStatus, EventType eventType);

    Page<Event> findAllByEventStatus_DescriptionAndTournament(
            String notStarted, Tournament tournament, PageRequest of);

    Page<Event> findAllByEventStatus_Description(
            String notStarted, PageRequest of);
}