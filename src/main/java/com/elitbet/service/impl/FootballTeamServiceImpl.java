package com.elitbet.service.impl;

import com.elitbet.model.Event;
import com.elitbet.model.Participant;
import com.elitbet.model.FootballTeam;
import com.elitbet.repository.FootballTeamRepository;
import com.elitbet.repository.ParticipantRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.FootballTeamService;
import com.elitbet.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FootballTeamServiceImpl extends FindById<FootballTeam, FootballTeamRepository>
        implements FootballTeamService {
    @Autowired
    FootballTeamRepository footballTeamRepository;
    @Autowired
    ParticipantService participantService;
    @Autowired
    ParticipantRepository participantRepository;

    @Override
    public FootballTeam update(Participant participant, String name, String statistics) {
        FootballTeam footballTeam = (FootballTeam) participant;
        footballTeam.setName(name);

        // TODO: 29.10.2018 Окремий метод цю лабуду
        String[] keyValueStrings = statistics.split(" ");
        Map<String, Object> statistic = new HashMap<>();
        for(String keyValueString: keyValueStrings){
            String[] keyValue = keyValueString.split(":");
            statistic.put(keyValue[0],keyValue[1]);
        }

        footballTeam.setGoals(Integer.parseInt(String.valueOf(statistic.get("goals"))));
        return footballTeamRepository.save(footballTeam);
    }

    @Override
    public FootballTeam create(Event event, String name) {
        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setName(name);
        footballTeam.setGoals(0);
        FootballTeam team = footballTeamRepository.save(footballTeam);
        Participant participant = participantService.findById(team.getParticipantId());
        participant.setEvent(event);
        participantRepository.save(participant);
        return team;
    }
}