package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.repository.EventRepository;
import com.elitbet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventResultService eventResultService;
    @Autowired
    EventTypeService eventTypeService;
    @Autowired
    ParticipantService participantService;
    @Autowired
    BetTypeService betTypeService;
    @Autowired
    SimpleDateFormat dateFormat;

    @Override
    public Event findById(String id) {
        Optional<Event> e = eventRepository.findById(id);
        return e.orElse(null);
    }

    @Override
    public List<Event> findAllNotStarted() {
        return eventRepository.getAllByStatusNotLike(Event.NOT_STARTED);
    }

    @Override
    public List<Event> findAllByEventType(String description) {
        EventType eventType = eventTypeService.findByDescription(description);
        return eventRepository.getAllByEventTypeEqualsAndStatusEqualsOrderByTime(eventType,Event.NOT_STARTED);
    }

    @Override
    public Event updateCoefficients(String id, String coefficientsString){
        Event event = findById(id);
        if(event!=null&&event.notStarted()){
            Map<BetType,Double> coefficientMap = coefficientsMap(coefficientsString);
            if(isValidCoefficients(coefficientMap)){
                for(EventResult eventResult : event.getResultList()){
                    BetType betType = eventResult.getBetType();
                    Double newCoefficient = coefficientMap.get(betType);
                    eventResultService.updateCoefficient(eventResult,newCoefficient);
                }
            }
        }
        return event;
    }
    
    @Override
    public Event create(String id, String eventTypeDescription, long time, String namesString,
                                             String tournament, String coefficientsString) {
        Event event = findById(id);
        if(event==null){
            List<String> participantNameList = participantNameList(namesString);
            Map<BetType,Double> coefficientMap = coefficientsMap(coefficientsString);
            event = create(id, eventTypeDescription,tournament,time, participantNameList);
            addParticipants(event, participantNameList);
            addEventResults(event, coefficientMap);
        }
        return event;
    }

    private Event create(String id, String eventTypeDescription, String tournament, long time, List<String> participantNameList) {
        Event event = new Event();
        event.setEventId(id);
        event.setTournament(tournament);
        event.setEventType(eventTypeService.findByDescription(eventTypeDescription));
        event.setTime(new Date(time));
        event.setDescription(description(time,tournament,participantNameList));
        event.setStatus(Event.NOT_STARTED);
        return eventRepository.save(event);
    }

    private void addParticipants(Event event, List<String> participantNameList) {
        ParticipantType participantType = event.getEventType().getParticipantType();
        for(String name: participantNameList){
            Participant participant = participantService.create(event,participantType,name);
            event.addParticipant(participant);
        }
        eventRepository.save(event);
    }

    private void addEventResults(Event event, Map<BetType,Double> coefficientMap) {
        if(isValidCoefficients(coefficientMap)){
            for(BetType betType: coefficientMap.keySet()){
                EventResult result = eventResultService.createEventResult(event,betType,coefficientMap.get(betType));
                event.addEventResult(result);
            }
            eventRepository.save(event);
        }
    }

    @Override
    public Event update(String id, String tournament, long time, String namesString, String statusFlashscore, String statisticsString) {
        Event event = findById(id);
        if(event!=null){
            event.setTime(new Date(time));
            event.setTournament(tournament);
            List<String> participantNameList = participantNameList(namesString);
            List<String> participantStatisticsList =  participantStatisticsList(statisticsString);
            event.setDescription(description(time,tournament,participantNameList));
            updateParticipants(event, participantNameList, participantStatisticsList);
            updateStatus(event, statusFlashscore);
        }
        return event;
    }

    private void updateParticipants(Event event, List<String> participantNameList, List<String> participantStatisticsList) {
        List<Participant> participantList = event.getParticipants();
        if(participantList.size()!=participantNameList.size()){
            return;
        }
        for(int i=0;i<participantList.size();i++){
            participantService.update(participantList.get(i), participantNameList.get(i), participantStatisticsList.get(i));
        }
    }

    private void updateStatus(Event event, String statusFlashscore) {
        String status = status(event.getEventType(),statusFlashscore);
        switch (status){
            case Event.FINISHED: handleFinish(event); break;
            case Event.POSTPONED: handlePostpone(event); break;
            case Event.STARTED: handleStart(event); break;
        }
    }

    private void handleStart(Event event) {
        updateStatusDescription(event, Event.STARTED);
    }

    private void handlePostpone(Event event) {
        if(event.notPostponed()){
            updateStatusDescription(event,Event.POSTPONED);
            calculateBets(event);
        }
    }

    private void handleFinish(Event event) {
        if (event.notFinished()) {
            updateStatusDescription(event,Event.FINISHED);
            updateEventResults(event);
            calculateBets(event);
        }
    }

    private void updateStatusDescription(Event event, String status){
        event.setStatus(status);
        eventRepository.save(event);
    }

    private void updateEventResults(Event event) {
        List<EventResult> resultList = event.getResultList();
        for(EventResult result: resultList){
            eventResultService.updateResult(event,result);
        }
    }

    private void calculateBets(Event event) {
        List<EventResult> resultList = event.getResultList();
        for(EventResult result: resultList){
            eventResultService.notifyResult(result);
        }
    }

    private String status(EventType eventType, String status) {
        switch (eventType.getDescription()){
            case EventType.football_match:
                switch (status){
                    case "Finished": case "After EP": case "After Pen.":
                        return Event.FINISHED;
                    case "Abandoned": case "Postponed": case "Interrupted": case "To finish": case "FRO":
                        return Event.POSTPONED;
                    case "":
                        return Event.NOT_STARTED;
                    default:
                        return Event.STARTED;
                }
        }
        return "Unsupported operation";
    }

    private String description(long time, String tournament, List<String> participantNameList) {
        String date = dateFormat.format(new Date(time));
        if(participantNameList.size()==1){
            return date + " " + tournament + ". " + participantNameList.get(0);
        } else {
            return date + " " + tournament + ". " + participantNameList.get(0) + " - " + participantNameList.get(1);
        }
    }

    private List<String> participantNameList(String s){
        return Arrays.asList(s.split(";"));
    }

    private List<String> participantStatisticsList(String s){
        return Arrays.asList(s.split(";"));
    }

    private Map<BetType, Double> coefficientsMap(String coefficients) {
        Map<BetType,Double> coefficientMap = new HashMap<>();
        String[] keyValueStrings = coefficients.split(";");
        for(String keyValueString: keyValueStrings){
            String[] keyValue = keyValueString.split(":");
            BetType betType = betTypeService.findByDescription(keyValue[0]);
            if(betType!=null){
                coefficientMap.put(betType, Double.valueOf(keyValue[1]));
            }
        }
        return coefficientMap;
    }

    private boolean isValidCoefficients(Map<BetType, Double> coefficientMap) {
        for(Double coefficient: coefficientMap.values()){
            if(coefficient == 0){
                return false;
            }
        }
        return true;
    }
}