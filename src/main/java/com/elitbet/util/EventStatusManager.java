package com.elitbet.util;

import com.elitbet.model.EventStatus;
import com.elitbet.model.EventType;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class EventStatusManager {

    private static Map<Map.Entry<String,String>, String> eventStatuses = new HashMap<>();

    private EventStatusManager() {
        add(EventType.FOOTBALL_MATCH,"Finished",EventStatus.FINISHED);
        add(EventType.FOOTBALL_MATCH,"After EP",EventStatus.FINISHED);
        add(EventType.FOOTBALL_MATCH,"After Pen.",EventStatus.FINISHED);
        add(EventType.FOOTBALL_MATCH,"Abandoned",EventStatus.POSTPONED);
        add(EventType.FOOTBALL_MATCH,"Interrupted",EventStatus.POSTPONED);
        add(EventType.FOOTBALL_MATCH,"Postponed",EventStatus.POSTPONED);
        add(EventType.FOOTBALL_MATCH,"To finish",EventStatus.POSTPONED);
        add(EventType.FOOTBALL_MATCH,"FRO",EventStatus.POSTPONED);
        add(EventType.FOOTBALL_MATCH,"",EventStatus.NOT_STARTED);
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
            return EventStatus.STARTED;
        }
        return status;
    }
}