package com.elitbet.util;

import com.elitbet.model.OutcomeType;
import com.elitbet.model.EventType;
import com.elitbet.model.Statistic;
import com.elitbet.service.OutcomeHandler;
import com.elitbet.service.handlers.FirstWinFootballMatchHandler;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class OutcomeHandlerManager {

    private Map<Map.Entry<String,String>,OutcomeHandler> outcomeHandlers = new HashMap<>();

    {
        outcomeHandlers.put(new AbstractMap.SimpleEntry<>(OutcomeType.FIRST_WIN,EventType.FOOTBALL_MATCH), new FirstWinFootballMatchHandler());
        // TODO: 02.11.2018 add other handlers
    }

    private static OutcomeHandlerManager ourInstance = new OutcomeHandlerManager();

    public static OutcomeHandlerManager getInstance() {
        return ourInstance;
    }

    public boolean result(String betTypeDescription, String eventTypeDescription, Statistic statistic, String parameters) {
        OutcomeHandler handler = outcomeHandlers.get(new AbstractMap.SimpleEntry<>(betTypeDescription,eventTypeDescription));
        return handler.execute(statistic, parameters);
    }
}
