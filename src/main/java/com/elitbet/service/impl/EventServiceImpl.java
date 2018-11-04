package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.repository.EventRepository;
import com.elitbet.service.*;
import com.elitbet.util.EventStatusManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    TournamentService tournamentService;
    @Autowired
    SimpleDateFormat dateFormat;

    @Override
    public Event findByFlashscoreId(String flashscoreId) {
        return eventRepository.getByFlashscoreIdEquals(flashscoreId);
    }

    @Override
    public List<Event> findAllNotStarted() {
        return eventRepository.findAllByEventStatus_Description(EventStatus.NOT_STARTED);
    }

    @Override
    public List<Event> findAllNotStartedFromTournament(Long tournamentId){
        return eventRepository.findAllByEventStatus_DescriptionAndTournament_TournamentId(
                EventStatus.NOT_STARTED, tournamentId);
    }

    @Override
    public Page<Event> findAllNotStarted(int page, int size) {
        return eventRepository.findAllByEventStatus_Description(
                Event.NOT_STARTED, PageRequest.of(page,size) );
    }

    @Override
    public Page<Event> findAllNotStartedFromTournament(Long tournamentId, int page, int size){
        return eventRepository.findAllByEventStatus_DescriptionAndTournament_TournamentId(
                Event.NOT_STARTED, tournamentId, PageRequest.of(page,size));
    }

    // TODO: 04.11.2018 delete in API 0.3
    @Override
    public void updateOdds(String flashscoreId, String oddsString){
        Event event = findByFlashscoreId(flashscoreId);
        Map<OutcomeType,Double> oddsMap = oddsMap(oddsString);
        if(event!=null&&event.getEventStatus().getDescription().equals(EventStatus.NOT_STARTED)&&isValidOdds(oddsMap)){
            for(Outcome outcome : event.getOutcomeList()){
                OutcomeType outcomeType = outcome.getOutcomeType();
                Double newCoefficient = oddsMap.get(outcomeType);
                outcomeService.updateOdds(outcome,newCoefficient);
            }
        }
    }

    // TODO: 04.11.2018 make private in API 0.3 and make more readable
    @Override
    public Event create(String flashscoreId, String eventTypeDescription, long time, String parameters,
                                             String tournamentDescription, String oddsString) {
        Event event = findByFlashscoreId(flashscoreId);
        if(event==null){
            event = new Event();
            event.setFlashscoreId(flashscoreId);
            Tournament tournament = tournamentService.findByDescription(tournamentDescription);
            event.setTournament(tournament);
            EventType eventType = eventTypeService.findByDescription(eventTypeDescription);
            EventStatus eventStatus = eventStatusService.findByDescription(Event.NOT_STARTED);
            event.setEventType(eventType);
            event.setEventStatus(eventStatus);
            event.setStartDateTime(new Date(time));
            Statistic statistic = statisticService.create(eventType, parameters);
            event.setStatistic(statistic);
            event.setDescription(description(event.getStartDateTime(),tournament.getDescription(),statistic.names()));
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

    // TODO: 04.11.2018 delete in API 0.3
    @Override
    public Event update(String id , String tournamentDescription, long time, String parameters, String statusFlashscore) {
        Event event = findByFlashscoreId(id);
        if(event!=null){
            if(isValidStartTimestamp(event.getEventStatus(),time)){
                event.setStartDateTime(new Date(time));
            }
            Tournament tournament = tournamentService.findByDescription(tournamentDescription);
            event.setTournament(tournament);
            Statistic statistic = event.getStatistic();
            statisticService.update(event.getEventType(), statistic, parameters);
            event.setDescription(description(event.getStartDateTime(),tournament.getDescription(),statistic.names()));
            updateStatus(event, statusFlashscore);
        }
        return event;
    }

    // TODO: 04.11.2018 for API 0.3
    @Override
    public Event update(String flashscoreId, String tournamentDescription, long time, String parametersString, String statusFlashscore, String oddsString, String eventType) {
        Event event = findByFlashscoreId(flashscoreId);
        if(event==null){
            return create(flashscoreId,eventType,time,parametersString,tournamentDescription,oddsString);
        }
        updateOdds(event,oddsString);
        updateStartTime(event,time);
        updateStatistic(event, parametersString);
        updateStatus(event,statusFlashscore);
        updateDescription(event);
        return event;
    }

    // TODO: 04.11.2018 for API 0.3
    private void updateOdds(Event event, String oddsString){
        Map<OutcomeType,Double> oddsMap = oddsMap(oddsString);
        if(event!=null&&event.getEventStatus().getDescription().equals(EventStatus.NOT_STARTED)&&isValidOdds(oddsMap)){
            for(Outcome outcome : event.getOutcomeList()){
                OutcomeType outcomeType = outcome.getOutcomeType();
                Double newCoefficient = oddsMap.get(outcomeType);
                outcomeService.updateOdds(outcome,newCoefficient);
            }
        }
    }

    private void updateDescription(Event event) {
        Date startDateTime = event.getStartDateTime();
        String tournamentDescription = event.getTournament().getDescription();
        String statisticNames = event.getStatistic().names();
        String newDescription = description(startDateTime,tournamentDescription,statisticNames);
        event.setDescription(newDescription);
    }

    private void updateStartTime(Event event, long time){
        if(isValidStartTimestamp(event.getEventStatus(),time)){
            event.setStartDateTime(new Date(time));
        }
    }

    private void updateStatistic(Event event,String parametersString){
        statisticService.update(event.getEventType(), event.getStatistic() , parametersString);
    }

    private void updateStatus(Event event, String statusFlashscore) {
        String status = EventStatusManager.getInstance().getStatus(
                event.getEventType().getDescription(),statusFlashscore);
        EventStatus eventStatus = eventStatusService.findByDescription(status);
        event.setEventStatus(eventStatus);
        eventRepository.save(event);
        if (Event.FINISHED.equals(status)) {
            updateOutcomesResults(event);
            calculateBets(event);
        } else if (Event.POSTPONED.equals(status)) {
            calculateBets(event);
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

    private String description(Date date, String tournament, String names) {
        String dateString = dateFormat.format(date);
        return dateString + " " + tournament + ". " + names;
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
    private boolean isValidStartTimestamp(EventStatus eventStatus, long startDateTime){
        long currentTimestamp = System.currentTimeMillis();
        return eventStatus.getDescription().equals(EventStatus.NOT_STARTED) || (startDateTime <= currentTimestamp);
    }
}