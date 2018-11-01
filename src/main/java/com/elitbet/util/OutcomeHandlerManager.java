package com.elitbet.util;

import com.elitbet.model.BetType;
import com.elitbet.model.EventType;
import com.elitbet.model.Participant;
import com.elitbet.service.OutcomeHandler;
import com.elitbet.service.handlers.FirstWinFootballMatchHandler;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutcomeHandlerManager {

    // TODO: 02.11.2018 change map key
    private Map<Map.Entry<String,String>,Class> outcomeHandlers = new HashMap<>();

    {
        outcomeHandlers.put(new AbstractMap.SimpleEntry<>(BetType.first_win,EventType.football_match),FirstWinFootballMatchHandler.class);
        // TODO: 02.11.2018 add other handlers
    }

    private static OutcomeHandlerManager ourInstance = new OutcomeHandlerManager();

    public static OutcomeHandlerManager getInstance() {
        return ourInstance;
    }

    public boolean result(String betTypeDescription, String eventTypeDescription,
                                 List<Participant> participantList, double parameter) throws IllegalAccessException, InstantiationException {
        Class outcomeHandlerClass = outcomeHandlers.get(new AbstractMap.SimpleEntry<>(betTypeDescription,eventTypeDescription));
        OutcomeHandler handler = (OutcomeHandler) outcomeHandlerClass.newInstance();
        return handler.execute(participantList,parameter);
    }
}
