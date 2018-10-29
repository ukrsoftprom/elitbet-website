package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.repository.EventResultRepository;
import com.elitbet.service.BetService;
import com.elitbet.service.EventResultService;
import com.elitbet.service.FindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventResultServiceImpl extends FindById<EventResult,EventResultRepository>
        implements EventResultService {
    @Autowired
    EventResultRepository eventResultRepository;
    @Autowired
    BetService betService;

    @Override
    public EventResult createEventResult(Event event, BetType betType, double coefficient) {
        EventResult eventResult = new EventResult();
        eventResult.setEvent(event);
        eventResult.setBetType(betType);
        eventResult.setCoefficient(coefficient);
        eventResult.setResult(false);
        return eventResultRepository.save(eventResult);
    }

    @Override
    public EventResult updateCoefficient(EventResult eventResult, double coefficient) {
        eventResult.setCoefficient(coefficient);
        return eventResultRepository.save(eventResult);
    }


    @Override
    public EventResult updateResult(Event event, EventResult eventResult) {
        String eventType = event.getEventType().getDescription();
        switch (eventType) {
            case EventType.football_match:
                return updateFootballMatchResult(event,eventResult);
        }
        return eventResult;
    }

    @Override
    public EventResult updateResult(EventResult eventResult, boolean result) {
        eventResult.setResult(result);
        return eventResultRepository.save(eventResult);
    }

    @Override
    public void notifyResult(EventResult eventResult) {
        for(Bet bet: betService.findAllByEventResult(eventResult)){
            betService.calculateBet(bet);
        }
    }

    @Override
    public EventResult updateFootballMatchResult(Event event, EventResult eventResult) {
        String betType = eventResult.getBetType().getDescription();
        switch (betType) {
            case BetType.first_win:
                return updateFootballMatchFirstWinResult(event, eventResult);
            case BetType.second_win:
                return updateFootballMatchSecondWinResult(event, eventResult);
            case BetType.draw:
                return updateFootballMatchDrawResult(event, eventResult);
        }
        return eventResult;
    }

    @Override
    public EventResult updateFootballMatchDrawResult(Event event, EventResult eventResult) {
        FootballTeam homeTeam = (FootballTeam) event.getParticipants().get(0);
        FootballTeam awayTeam = (FootballTeam) event.getParticipants().get(1);
        return updateResult(eventResult,homeTeam.getGoals()==awayTeam.getGoals());
    }

    @Override
    public EventResult updateFootballMatchSecondWinResult(Event event, EventResult eventResult) {
        FootballTeam homeTeam = (FootballTeam) event.getParticipants().get(0);
        FootballTeam awayTeam = (FootballTeam) event.getParticipants().get(1);
        return updateResult(eventResult,homeTeam.getGoals()<awayTeam.getGoals());
    }

    @Override
    public EventResult updateFootballMatchFirstWinResult(Event event, EventResult eventResult) {
        FootballTeam homeTeam = (FootballTeam) event.getParticipants().get(0);
        FootballTeam awayTeam = (FootballTeam) event.getParticipants().get(1);
        return updateResult(eventResult,homeTeam.getGoals()>awayTeam.getGoals());
    }
}
