package com.elitbet.service.handlers;

import com.elitbet.model.FootballTeam;
import com.elitbet.model.Participant;
import com.elitbet.service.OutcomeHandler;

import java.util.List;

public class FirstWinFootballMatchHandler implements OutcomeHandler {
    @Override
    public boolean execute(List<Participant> participantList, double parameter) {
        FootballTeam homeTeam = (FootballTeam) participantList.get(0);
        FootballTeam awayTeam = (FootballTeam) participantList.get(1);
        return homeTeam.getGoals() > awayTeam.getGoals();
    }
}