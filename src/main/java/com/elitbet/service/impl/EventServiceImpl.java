package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.repository.EventRepository;
import com.elitbet.service.*;
import com.elitbet.util.EventStatusManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    OutcomeService outcomeService;
    @Autowired
    EventTypeService eventTypeService;
    @Autowired
    EventStatusService eventStatusService;
    @Autowired
    StatisticService statisticService;
    @Autowired
    OutcomeTypeService outcomeTypeService;
    @Autowired
    SimpleDateFormat dateFormat;

    @Override
    public Event findByFlashscoreId(String flashscoreId) {
        return eventRepository.getByFlashscoreIdEquals(flashscoreId);
    }

    @Override
    public List<Event> findAllNotStarted() {
        return eventRepository.getAllByEventStatus_DescriptionNotLike(Event.NOT_STARTED);
    }

    @Override
    public List<Event> findAllByEventType(String description) {
        EventType eventType = eventTypeService.findByDescription(description);
        EventStatus notStarted = eventStatusService.findByDescription(Event.NOT_STARTED);
        return eventRepository.getAllByEventStatusEqualsAndEventTypeEqualsOrderByStartDateTime(notStarted, eventType);
    }

    @Override
    public void updateOutcomeOdds(String flashscoreId, String oddsString){
        Event event = findByFlashscoreId(flashscoreId);
        Map<OutcomeType,Double> oddsMap = oddsMap(oddsString);
        if(event!=null&&event.notStarted()&&isValidOdds(oddsMap)){
            for(Outcome outcome : event.getOutcomeList()){
                OutcomeType outcomeType = outcome.getOutcomeType();
                Double newCoefficient = oddsMap.get(outcomeType);
                outcomeService.updateOdds(outcome,newCoefficient);
            }
        }
    }
    
    @Override
    public Event create(String flashscoreId, String eventTypeDescription, long time, String parameters,
                                             String tournament, String oddsString) {
        Event event = findByFlashscoreId(flashscoreId);
        if(event==null){
            event = new Event();
            event.setFlashscoreId(flashscoreId);
            event.setTournament(tournament);
            EventType eventType = eventTypeService.findByDescription(eventTypeDescription);
            EventStatus eventStatus = eventStatusService.findByDescription(Event.NOT_STARTED);
            event.setEventType(eventType);
            event.setEventStatus(eventStatus);
            event.setStartDateTime(new Date(time));
            Statistic statistic = statisticService.create(eventType, parameters);
            event.setStatistic(statistic);
            Statistic created = event.getStatistic();
            event.setDescription(description(time,tournament,created.names()));
            event = eventRepository.save(event);
            addOutcomes(event, oddsMap(oddsString));
        }
        return event;
    }

    private void addOutcomes(Event event, Map<OutcomeType,Double> oddsMap) {
        if(isValidOdds(oddsMap)){
            List<Outcome> outcomeList = new ArrayList<>();
            for(OutcomeType outcomeType : oddsMap.keySet()){
                outcomeList.add(outcomeService.createOutcome(event, outcomeType,oddsMap.get(outcomeType)));
            }
            event.setOutcomeList(outcomeList);
            eventRepository.save(event);
        }
    }

    @Override
    public Event update(String id , String tournament, long time, String parameters, String statusFlashscore) {
        Event event = findByFlashscoreId(id);
        if(event!=null&&isValidStartTimestamp(time)){
            event.setStartDateTime(new Date(time));
            event.setTournament(tournament);
            Statistic statistic = event.getStatistic();
            statisticService.update(event.getEventType(), statistic, parameters);
            event.setDescription(description(time,tournament,statistic.names()));
            updateStatus(event, statusFlashscore);
        }
        return event;
    }

    private void updateStatus(Event event, String statusFlashscore) {
        String status = EventStatusManager.getInstance().getStatus(
                event.getEventType().getDescription(),statusFlashscore);
        event.setEventStatus(eventStatusService.findByDescription(status));
        switch (status){
            case Event.FINISHED: {
                updateOutcomesResults(event);
                calculateBets(event);
                break;
            }
            case Event.POSTPONED: {
                calculateBets(event);
                break;
            }
        }
    }

    private void updateOutcomesResults(Event event) {
        List<Outcome> outcomeList = event.getOutcomeList();
        for(Outcome outcome: outcomeList){
            outcomeService.updateOutcomeResult(outcome);
        }
    }

    // TODO: 02.11.2018 у багатопоточності забрати
    private void calculateBets(Event event) {
        List<Outcome> outcomeList = event.getOutcomeList();
        for(Outcome outcome: outcomeList){
            outcomeService.notifyResult(outcome);
        }
    }

    private String description(long time, String tournament, String names) {
        String date = dateFormat.format(new Date(time));
        return date + " " + tournament + ". " + names;
    }

    private Map<OutcomeType, Double> oddsMap(String oddsString) {
        Map<OutcomeType,Double> oddsMap = new HashMap<>();
        String[] keyValueStrings = oddsString.split(";");
        for(String keyValueString: keyValueStrings){
            String[] keyValue = keyValueString.split(":");
            OutcomeType outcomeType = outcomeTypeService.findByDescription(keyValue[0]);
            if(outcomeType !=null){
                oddsMap.put(outcomeType, Double.valueOf(keyValue[1]));
            }
        }
        return oddsMap;
    }

    // TODO: 02.11.2018 validator
    private boolean isValidOdds(Map<OutcomeType, Double> oddsMap) {
        for(Double odds: oddsMap.values()){
            if(odds == 0){
                return false;
            }
        }
        return true;
    }

    // TODO: 02.11.2018 validator
    private boolean isValidStartTimestamp(long startDateTime){
        long currentTimestamp = System.currentTimeMillis();
        return (startDateTime<currentTimestamp);
    }
}