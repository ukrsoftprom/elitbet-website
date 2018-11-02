package com.elitbet.service;

import com.elitbet.model.Event;

import java.util.List;

public interface EventService {
    Event findByFlashscoreId(String id);

    List<Event> findAllNotStarted();

    List<Event> findAllByEventType(String description);

    void updateOutcomeOdds(String id, String oddsString);

    Event create(String id, String eventType, long time, String names, String tournament, String odds);

    Event update(String id, String tournament, long time, String parameters, String statusFlashscore);
}