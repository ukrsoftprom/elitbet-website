package com.elitbet.util;

import com.elitbet.model.*;
import com.elitbet.repository.*;
import com.elitbet.service.OutcomeHandler;
import com.elitbet.service.handlers.FirstWinFootballMatchHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

// TODO: 02.11.2018 finish this poebota
@Service
public class DatabaseManager {
    @Autowired
    EventStatusRepository eventStatusRepository;
    @Autowired
    OutcomeStatusRepository outcomeStatusRepository;
    @Autowired
    WagerStatusRepository wagerStatusRepository;
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Autowired
    OutcomeTypeRepository outcomeTypeRepository;

    private static Map<Status, Object> objectMap = new HashMap<>();
    {
//        add(Status.OUTCOME_NO_STATUS, outcomeStatusRepository);
//        add(Status.OUTCOME_NOT_PASSED,outcomeStatusRepository);
//        add(Status.OUTCOME_PASSED,outcomeStatusRepository);
//        add(Status.OUTCOME_RETURNED,outcomeStatusRepository);
//        add(Status.WAGER_NO_STATUS,wagerStatusRepository);
//        add(Status.WAGER_NOT_PASSED,wagerStatusRepository);
//        add(Status.WAGER_PASSED,wagerStatusRepository);
//        add(Status.WAGER_RETURNED,wagerStatusRepository);
//        add(Status.EVENT_NOT_STARTED,eventStatusRepository);
//        add(Status.EVENT_STARTED,eventStatusRepository);
//        add(Status.EVENT_POSTPONED,eventStatusRepository);
//        add(Status.EVENT_FINISHED,eventStatusRepository);
//        add(Status.EVENT_TYPE_FOOTBALL_MATCH,eventTypeRepository);
//        add(Status.OUTCOME_TYPE_FIRST_WIN,outcomeTypeRepository);
//        add(Status.OUTCOME_TYPE_SECOND_WIN,outcomeTypeRepository);
//        add(Status.OUTCOME_TYPE_DRAW,outcomeTypeRepository);
    }

    private static void add(Status status, FindByDescription repository){
        objectMap.put(status,repository.findByDescriptionEquals(status.name()));
    }

    private static OutcomeHandlerManager ourInstance = new OutcomeHandlerManager();

    public static OutcomeHandlerManager getInstance() {
        return ourInstance;
    }

    public Object get(Status status){
        return objectMap.get(status);
    }
}
