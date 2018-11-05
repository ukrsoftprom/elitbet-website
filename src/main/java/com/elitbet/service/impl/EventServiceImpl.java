package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.repository.EventRepository;
import com.elitbet.service.*;
import com.elitbet.util.EventStatusManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    OutcomeService outcomeService;
    @Autowired
    StatusService statusService;
    @Autowired
    StatisticService statisticService;
    @Autowired
    TournamentService tournamentService;

    @Override
    public Event findByFlashscoreId(String flashscoreId) {
        return eventRepository.getByFlashscoreIdEquals(flashscoreId);
    }

    @Override
    public List<Event> findAllNotStarted() {
        return eventRepository.findAllByEventStatus_Description(
                Status.EVENT_NOT_STARTED);
    }

    @Override
    public List<Event> findAllNotStartedFromTournament(Long tournamentId){
        return eventRepository.findAllByEventStatus_DescriptionAndTournament_TournamentId(
                Status.EVENT_NOT_STARTED, tournamentId);
    }

    @Override
    public Page<Event> findAllNotStarted(int page, int size) {
        return eventRepository.findAllByEventStatus_Description(
                Status.EVENT_NOT_STARTED, PageRequest.of(page,size) );
    }

    @Override
    public Page<Event> findAllNotStartedFromTournament(Long tournamentId, int page, int size){
        return eventRepository.findAllByEventStatus_DescriptionAndTournament_TournamentId(
                Status.EVENT_NOT_STARTED, tournamentId, PageRequest.of(page,size));
    }

    @Override
    public void updateOdds(String flashscoreId, String oddsString){
        Event event = findByFlashscoreId(flashscoreId);
        Map<OutcomeType,Double> oddsMap = oddsMap(oddsString);
        if(event!=null&&event.getEventStatus().getDescription().equals(Status.EVENT_NOT_STARTED)&&isValidOdds(oddsMap)){
            for(Outcome outcome : event.getOutcomeList()){
                OutcomeType outcomeType = outcome.getOutcomeType();
                Double newCoefficient = oddsMap.get(outcomeType);
                outcomeService.updateOdds(outcome,newCoefficient);
            }
        }
    }

    @Override
    public Event create(String flashscoreId, String eventTypeDescription, long time, String parameters,
                                             String tournamentDescription, String oddsString) {
        Event event = findByFlashscoreId(flashscoreId);
        if(event==null){
            EventType eventType = statusService.getEventType(eventTypeDescription);
            EventStatus eventStatus = statusService.getEventStatus(Status.EVENT_NOT_STARTED);
            Statistic statistic = statisticService.create(eventType, parameters);

            event = new Event();
            event.setFlashscoreId(flashscoreId);
            event.setEventType(eventType);
            event.setEventStatus(eventStatus);
            event.setStartDateTime(new Date(time));
            event.setStatistic(statistic);
            eventRepository.save(event);

            addTournament(event, tournamentDescription);
            addOutcomes(event, oddsMap(oddsString));
        }
        return event;
    }

    private void addTournament(Event event, String tournamentDescription){
        Tournament tournament = tournamentService.findByDescription(tournamentDescription);
        event.setTournament(tournament);
        tournament.addEvent(event);
        eventRepository.save(event);
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
    public Event update(String id , String tournamentDescription, long time, String parameters, String statusFlashscore) {
        Event event = findByFlashscoreId(id);
        if(event!=null){
            updateStartTime(event, time);
            updateStatistic(event, parameters);
            updateStatus(event, statusFlashscore);
        }
        return event;
    }

    private void updateStartTime(Event event, long time){
        if(isValidStartTimestamp(event.getEventStatus(),time)){
            event.setStartDateTime(new Date(time));
        }
    }

    private void updateStatistic(Event event,String parametersString){
        Statistic statistic = event.getStatistic();
        EventType eventType = event.getEventType();
        statisticService.update(eventType, statistic , parametersString);
    }

    private void updateStatus(Event event, String statusFlashscore) {
        String eventType = event.getEventType().getDescription();
        String status = EventStatusManager.getInstance().getStatus(eventType,statusFlashscore);
        if (status.equals(Status.EVENT_POSTPONED) || status.equals(Status.EVENT_FINISHED)) {
            if (!event.getEventStatus().getDescription().equals(status)) {
                updateStatusDescription(event, status);
                updateOutcomesResults(event);
            }
        } else if (status.equals(Status.EVENT_STARTED)) {
            if (!event.getEventStatus().getDescription().equals(status)) {
                updateStatusDescription(event, status);
            }
        }
    }

    private void updateStatusDescription(Event event, String description){
        EventStatus status = statusService.getEventStatus(description);
        event.setEventStatus(status);
        eventRepository.save(event);
    }

    private void updateOutcomesResults(Event event) {
        List<Outcome> outcomeList = event.getOutcomeList();
        for(Outcome outcome: outcomeList){
            outcomeService.updateOutcomeResult(outcome);
        }
    }

    private Map<OutcomeType, Double> oddsMap(String oddsString) {
        Map<OutcomeType,Double> oddsMap = new LinkedHashMap<>();
        String[] keyValueStrings = oddsString.split(";");
        for(String keyValueString: keyValueStrings){
            String[] keyValue = keyValueString.split(":");
            OutcomeType outcomeType = statusService.getOutcomeType(keyValue[0]);
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
    private boolean isValidStartTimestamp(EventStatus eventStatus, long startDateTime){
        long currentTimestamp = System.currentTimeMillis();
        String eventStatusDesc = eventStatus.getDescription();
        return eventStatusDesc.equals(Status.EVENT_NOT_STARTED) || (startDateTime <= currentTimestamp);
    }
}