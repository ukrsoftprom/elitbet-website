package com.elitbet.util;

import com.elitbet.model.ParticipantType;
import com.elitbet.service.FootballTeamService;
import com.elitbet.service.IParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class ParticipantManager {
    @Autowired
    ApplicationContext context;

    private Map<String, Class> services = new HashMap<>();

    private ParticipantManager() {
        services.put(ParticipantType.football_team, FootballTeamService.class);
    }

    private static class ParticipantManagerHolder{
        private static final ParticipantManager participantManager = new ParticipantManager();
    }

    public static ParticipantManager getInstance(){
        return ParticipantManagerHolder.participantManager;
    }

    public IParticipant getService(String participantName){
        return (IParticipant) context.getBean(services.get(participantName));
    }
}