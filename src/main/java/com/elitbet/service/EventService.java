package com.elitbet.service;

import com.elitbet.model.BetType;
import com.elitbet.model.Event;
import com.elitbet.model.EventType;

import java.util.List;
import java.util.Map;

public interface EventService {
    Event findById(String eventId);
    List<Event> findAllByEventType(String description);

    Event updateIfNotExistCreateEvent(String id, String eventType, long time, String names, String tournament, String coefficients);

    Event create(String id, EventType eventType, String tournament, long time, String[] participants);
    Event addEventResults(Event event, Map<BetType,Double> coefficients);
    Event addParticipants(Event event, String[] participantNames);

    Event update(String id, String tournament, long time, String names, String status, String results);
    Event updateCoefficients(Event event, Map<BetType, Double> coefficients);
    Event updateParticipants(Event event, String names, String statistics);

    String[] statisticsBuilder(String statistics);

    Event updateStatus(Event event, String status);

    // TODO: 29.10.2018 Поміняти сигнатуру
    Event updateEventResults(Event event);

    String statusBuilder(EventType eventType, String status);
    String descriptionBuilder(long time, String tournament, String[] names);

    // TODO: 29.10.2018 В один метод де апдейтиться статус???
    Event handleFinish(Event event);
    Event handleStart(Event event);
    Event handlePostpone(Event event);

    // TODO: 29.10.2018 Поміняти сигнатуру
    Event calculateBets(Event event);


    String[] namesBuilder(String names);

    boolean hasZeroCoefficient(Map<BetType, Double> coefficientMap);

    Map<BetType, Double> coefficientMap(String coefficients);
}