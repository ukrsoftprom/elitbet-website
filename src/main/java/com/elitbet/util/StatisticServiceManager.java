package com.elitbet.util;

import com.elitbet.model.EventType;
import com.elitbet.service.FootballMatchStatisticService;

import java.util.HashMap;
import java.util.Map;

public class StatisticServiceManager {

    private Map<String, Class> services = new HashMap<>();

    private StatisticServiceManager() {
        services.put(EventType.FOOTBALL_MATCH, FootballMatchStatisticService.class);
    }

    private static class StatisticManagerHolder{
        private static final StatisticServiceManager StatisticServiceManager = new StatisticServiceManager();
    }

    public static StatisticServiceManager getInstance(){
        return StatisticManagerHolder.StatisticServiceManager;
    }

    public Class getServiceName(String StatisticName){
        return services.get(StatisticName);
    }
}