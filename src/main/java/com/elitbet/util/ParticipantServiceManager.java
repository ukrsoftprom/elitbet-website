package com.elitbet.util;

import com.elitbet.model.ParticipantType;
import com.elitbet.service.FootballTeamService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ParticipantServiceManager {

    private Map<String, Class> services = new HashMap<>();

    // TODO: 02.11.2018 new outcomesHandlerManager class
    // TODO: 02.11.2018 add handlers for each event and bettype
    private Map<Class,Method> handlers = new HashMap<>();

    private ParticipantServiceManager() {
        services.put(ParticipantType.football_team, FootballTeamService.class);
    }

    private static class ParticipantManagerHolder{
        private static final ParticipantServiceManager participantServiceManager = new ParticipantServiceManager();
    }

    public static ParticipantServiceManager getInstance(){
        return ParticipantManagerHolder.participantServiceManager;
    }

    public Class getServiceName(String participantName){
        return services.get(participantName);
    }
}