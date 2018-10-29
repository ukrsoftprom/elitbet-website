package com.elitbet.util;

import com.elitbet.model.ParticipantType;
import com.elitbet.service.FootballTeamService;

import java.util.HashMap;
import java.util.Map;

public class ParticipantManager {
    private Map<String, Class> services = new HashMap<>();

    public ParticipantManager() {
        services.put(ParticipantType.football_team, FootballTeamService.class);
    }

    private static class ParticipantManagerHolder{
        private static final ParticipantManager participantManager = new ParticipantManager();
    }

    public static ParticipantManager getInstance(){
        return ParticipantManagerHolder.participantManager;
    }
    public Class getService(String participantName){
        return services.get(participantName);
    }
}
