package com.elitbet.util;

import com.elitbet.model.OutcomeType;
import com.elitbet.model.EventType;
import com.elitbet.model.Statistic;
import com.elitbet.service.OutcomeHandler;
import com.elitbet.service.handlers.DrawFootballMatchHandler;
import com.elitbet.service.handlers.FirstWinFootballMatchHandler;
import com.elitbet.service.handlers.SecondWinFootballMatchHandler;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class OutcomeHandlerManager {

    private Map<Map.Entry<String,String>,OutcomeHandler> outcomeHandlersMap = new HashMap<>();

    {
        add(OutcomeType.FIRST_WIN,EventType.FOOTBALL_MATCH, new FirstWinFootballMatchHandler());
        add(OutcomeType.SECOND_WIN,EventType.FOOTBALL_MATCH,new SecondWinFootballMatchHandler());
        add(OutcomeType.DRAW,EventType.FOOTBALL_MATCH,new DrawFootballMatchHandler());
    }

    private void add(String outcomeType, String eventType, OutcomeHandler outcomeHandler){
        outcomeHandlersMap.put(new AbstractMap.SimpleEntry<>(outcomeType,eventType),outcomeHandler);
    }

    private static OutcomeHandlerManager ourInstance = new OutcomeHandlerManager();

    public static OutcomeHandlerManager getInstance() {
        return ourInstance;
    }

    public boolean result(String outcomeType, String eventType, Statistic statistic, String parameters) {
        Map.Entry<String,String> key = new AbstractMap.SimpleEntry<>(outcomeType,eventType);
        OutcomeHandler handler = outcomeHandlersMap.get(key);
        return handler.execute(statistic, parameters);
    }
}
