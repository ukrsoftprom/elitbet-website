package com.elitbet.service.handlers;

import com.elitbet.model.FootballMatchStatistic;
import com.elitbet.model.Statistic;
import com.elitbet.service.OutcomeHandler;

public class DrawFootballMatchHandler implements OutcomeHandler {

    @Override
    public boolean execute(Statistic statistic, String parameters) {
        int homeGoals = ((FootballMatchStatistic) statistic).getHomeGoals();
        int awayGoals = ((FootballMatchStatistic) statistic).getAwayGoals();
        return homeGoals==awayGoals;
    }
}
