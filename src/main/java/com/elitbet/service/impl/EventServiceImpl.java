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
    FootballTeamService footballTeamService;
    @Autowired
    BetTypeService betTypeService;
    @Autowired
    SimpleDateFormat dateFormat;

    @Override
    public Event create(String id, EventType eventType,String tournament, long time, String[] names) {
        Event event = new Event();
        event.setEventId(id);
        event.setTournament(tournament);
        event.setEventType(eventType);
        event.setTime(new Date(time));
        event.setDescription(descriptionBuilder(time,tournament,names));
        event.setStatus(Event.not_started);
        return eventRepository.save(event);
    }

    @Override
    public Event updateCoefficients(Event event, Map<BetType, Double> coefficients){
        for(EventResult eventResult: event.getResultList()){
            BetType betType = eventResult.getBetType();
            Double newCoefficient = coefficients.get(betType);
            eventResultService.updateCoefficient(eventResult,newCoefficient);
        }
        return event;
    }

    @Override
    public Event addEventResults(Event event, Map<BetType, Double> coefficients) {
        List<EventResult> resultList = new ArrayList<>();
        for(BetType betType: coefficients.keySet()){
            resultList.add(eventResultService.createEventResult(event,betType,coefficients.get(betType)));
        }
        event.setResultList(resultList);
        return eventRepository.save(event);
    }

    @Override
    public Event findById(String id) {
        Optional<Event> e = eventRepository.findById(id);
        return e.orElse(null);
    }

    @Override
    public Event addParticipants(Event event, String[] participantNames) {
        ParticipantType participantType = event.getEventType().getParticipantType();
        List<Participant> participants = new ArrayList<>();
        for(String name:participantNames){
            participants.add(participantService.create(event,participantType,name));
        }
        event.setParticipants(participants);
        return eventRepository.save(event);
    }

    @Override
    public Event update(String id, String tournament, long time, String names, String status, String results) {
        Event event = findById(id);
        // TODO: 29.10.2018 update time tournament description
        if(event!=null){
            status = statusBuilder(event.getEventType(),status);
            switch (status){
                case Event.finished:
                    return handleFinish(event);
                case Event.postponed:
                    handlePostpone(event); break;
                case Event.started:
                    handleStart(event); break;
            }
            updateParticipants(event,names,results);
        }
        return event;
    }

    @Override
    public Event updateParticipants(Event event, String names, String statistics) {
        String[] nameArray = namesBuilder(names);
        String[] statisticArray = statisticsBuilder(statistics);
        List<Participant> participantList = event.getParticipants();
        int participantNumber = participantList.size();
        if(participantNumber!=nameArray.length||participantNumber!=statisticArray.length){
            System.out.println("Error updating participants");
        } else {
            for(int i = 0;i< participantNumber;i++){
                participantService.update(participantList.get(i),nameArray[i], statisticArray[i]);
            }
        }
        return event;
    }

    @Override
    public String[] statisticsBuilder(String statistics) {
        return statistics.split(";");
    }

    @Override
    public Event updateStatus(Event event, String status){
        event.setStatus(status);
        return eventRepository.save(event);
    }

    @Override
    public Event handleFinish(Event event) {
        if (event.isFinished()) {
            return null;
        }
        updateStatus(event,Event.finished);
        updateEventResults(event);
        calculateBets(event);
        return event;
    }

    @Override
    public List<Event> findAllByEventType(String description) {
        EventType eventType = eventTypeService.findByDescription(description);
        return eventRepository.getAllByEventTypeEqualsAndStatusEqualsOrderByTime(eventType,Event.not_started);
    }

    @Override
    public String statusBuilder(EventType eventType, String status) {
        switch (eventType.getDescription()){
            case EventType.football_match:
                switch (status){
                    case "Finished": case "After EP": case "After Pen.":
                        return Event.finished;
                    // todo "FRO" into not unsupported bets because undefined start time
                    case "Abandoned": case "Postponed": case "Interrupted": case "To finish": case "FRO":
                        return Event.postponed;
                    case "":
                        return Event.not_started;
                    default:
                        return Event.started;
                }
        }
        return "Unsupported operation";
    }

    @Override
    public Event handleStart(Event event) {
        if(event.isStarted()){
            return null;
        }
        updateStatus(event, Event.started);
        return event;
    }

    @Override
    public Event handlePostpone(Event event) {
        if(event.isPostponed()){
            return null;
        }
        updateStatus(event,Event.postponed);
        calculateBets(event);
        return event;
    }

    @Override
    public String descriptionBuilder(long time, String tournament, String[] names) {
        String date = dateFormat.format(new Date(time));
        if(names.length==1){
            return date + " " + tournament + ". " + names[0];
        } else {
            return date + " " + tournament + ". " + names[0] + " - " + names[1];
        }
    }


    @Override
    public Event updateEventResults(Event event) {
        List<EventResult> resultList = event.getResultList();
        for(EventResult result: resultList){
            eventResultService.updateResult(event,result);
        }
        return event;
    }

    @Override
    public Event calculateBets(Event event) {
        List<EventResult> resultList = event.getResultList();
        for(EventResult result: resultList){
            eventResultService.notifyResult(result);
        }
        return event;
    }

    @Override
    public Event updateIfNotExistCreateEvent(String id, String eventTypeDescription, long time, String names, String tournament, String coefficients) {
        Map<BetType,Double> coefficientMap = coefficientMap(coefficients);
        String[] participantNames = namesBuilder(names);
        EventType eventType = eventTypeService.findByDescription(eventTypeDescription);
        if(hasZeroCoefficient(coefficientMap)){
            System.out.println("Invalid coefficient");
            return null;
        }
        Event event = findById(id);
        if(event!=null&&event.isFinished()){
            return event;
        } else if(event!=null&&event.notStarted()){
            updateCoefficients(event,coefficientMap);
        } else if(event==null) {
            event = create(id, eventType,tournament,time, participantNames);
            event = addParticipants(event, participantNames);
            event = addEventResults(event, coefficientMap);
        }
        return event;
    }

    @Override
    public String[] namesBuilder(String names){
        return names.split(";");
    }

    @Override
    public boolean hasZeroCoefficient(Map<BetType, Double> coefficientMap) {
        for(Double coefficient: coefficientMap.values()){
            if(coefficient== 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<BetType, Double> coefficientMap(String coefficients) {
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
}