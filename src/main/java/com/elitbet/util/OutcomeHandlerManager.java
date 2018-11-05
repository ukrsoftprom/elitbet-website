package com.elitbet.util;

import com.elitbet.model.Status;
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
        add(Status.OUTCOME_TYPE_FIRST_WIN,Status.EVENT_TYPE_FOOTBALL_MATCH, new FirstWinFootballMatchHandler());
        add(Status.OUTCOME_TYPE_SECOND_WIN,Status.EVENT_TYPE_FOOTBALL_MATCH,new SecondWinFootballMatchHandler());
        add(Status.OUTCOME_TYPE_DRAW,Status.EVENT_TYPE_FOOTBALL_MATCH,new DrawFootballMatchHandler());
    }

    private void add(String outcomeType, String eventType, OutcomeHandler outcomeHandler){
        outcomeHandlersMap.put(new AbstractMap.SimpleEntry<>(outcomeType,eventType),outcomeHandler);
    }

    private static OutcomeHandlerManager ourInstance = new OutcomeHandlerManager();

    public static OutcomeHandlerManager getInstance() {
        return ourInstance;
    }

    public String result(String outcomeType, String eventType, Statistic statistic, String parameters) {
        Map.Entry<String,String> key = new AbstractMap.SimpleEntry<>(outcomeType,eventType);
        OutcomeHandler handler = outcomeHandlersMap.get(key);
        return handler.execute(statistic, parameters);
    }
}
