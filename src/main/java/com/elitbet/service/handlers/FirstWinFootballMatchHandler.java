package com.elitbet.service.handlers;

import com.elitbet.model.FootballMatchStatistic;
import com.elitbet.model.Status;
import com.elitbet.model.Statistic;
import com.elitbet.service.OutcomeHandler;

public class FirstWinFootballMatchHandler implements OutcomeHandler {

    @Override
    public String execute(Statistic statistic, String parameters) {
        int homeGoals = ((FootballMatchStatistic) statistic).getHomeGoals();
        int awayGoals = ((FootballMatchStatistic) statistic).getAwayGoals();
        if(homeGoals>awayGoals) {
            return Status.OUTCOME_PASSED;
        } else {
            return Status.OUTCOME_NOT_PASSED;
        }
    }
}