package com.elitbet.util;

import com.elitbet.model.EventStatus;
import com.elitbet.model.EventType;
import com.elitbet.model.Status;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class EventStatusManager {

    private static Map<Map.Entry<String,String>, String> eventStatuses = new HashMap<>();

    private EventStatusManager() {
        add(Status.EVENT_TYPE_FOOTBALL_MATCH,"Finished",Status.EVENT_FINISHED);
        add(Status.EVENT_TYPE_FOOTBALL_MATCH,"After EP",Status.EVENT_FINISHED);
        add(Status.EVENT_TYPE_FOOTBALL_MATCH,"After Pen.",Status.EVENT_FINISHED);
        add(Status.EVENT_TYPE_FOOTBALL_MATCH,"Abandoned",Status.EVENT_POSTPONED);
        add(Status.EVENT_TYPE_FOOTBALL_MATCH,"Interrupted",Status.EVENT_POSTPONED);
        add(Status.EVENT_TYPE_FOOTBALL_MATCH,"Postponed",Status.EVENT_POSTPONED);
        add(Status.EVENT_TYPE_FOOTBALL_MATCH,"To finish",Status.EVENT_POSTPONED);
        add(Status.EVENT_TYPE_FOOTBALL_MATCH,"FRO",Status.EVENT_POSTPONED);
        add(Status.EVENT_TYPE_FOOTBALL_MATCH,"",Status.EVENT_NOT_STARTED);
    }

    private static void add(String eventType, String flashscoreStatus, String eventStatus){
        Map.Entry<String,String> key = new AbstractMap.SimpleEntry<>(eventType,flashscoreStatus);
        eventStatuses.put(key,eventStatus);
    }

    private static class StatisticManagerHolder{
        private static final EventStatusManager EventStatusManager = new EventStatusManager();
    }

    public static EventStatusManager getInstance(){
        return StatisticManagerHolder.EventStatusManager;
    }

    public String getStatus(String eventType, String flashscoreStatus){
        Map.Entry<String,String> key = new AbstractMap.SimpleEntry<>(eventType,flashscoreStatus);
        String status = eventStatuses.get(key);
        if(status==null){
            return Status.EVENT_STARTED;
        }
        return status;
    }
}