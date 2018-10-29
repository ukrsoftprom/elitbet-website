package com.elitbet.service;

import com.elitbet.model.BetType;
import com.elitbet.model.Event;
import com.elitbet.model.EventResult;
import org.springframework.stereotype.Service;

@Service
public interface EventResultService {
    EventResult findById(Long EventResultId);
    EventResult createEventResult(Event event, BetType betType, double coefficient);
    EventResult updateCoefficient(EventResult eventResult, double coefficient);
    EventResult updateResult(Event event, EventResult result);
    EventResult updateResult(EventResult eventResult, boolean result);
    void notifyResult(EventResult eventResult);

    EventResult updateFootballMatchResult(Event event, EventResult eventResult);

    EventResult updateFootballMatchDrawResult(Event event, EventResult eventResult);

    EventResult updateFootballMatchSecondWinResult(Event event, EventResult eventResult);

    EventResult updateFootballMatchFirstWinResult(Event event, EventResult eventResult);
}
