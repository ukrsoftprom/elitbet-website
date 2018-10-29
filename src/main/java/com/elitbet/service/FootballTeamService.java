package com.elitbet.service;

import com.elitbet.model.Event;
import com.elitbet.model.Participant;
import com.elitbet.model.FootballTeam;

public interface FootballTeamService extends IParticipant {
    FootballTeam findById(Long participantId);
    FootballTeam update(Participant participant, String name, String statistic);
    FootballTeam create(Event event, String name);
}
