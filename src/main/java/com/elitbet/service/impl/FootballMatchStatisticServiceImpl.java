package com.elitbet.service.impl;

import com.elitbet.model.FootballMatchStatistic;
import com.elitbet.model.Statistic;
import com.elitbet.repository.FootballMatchStatisticRepository;
import com.elitbet.repository.StatisticRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.FootballMatchStatisticService;
import com.elitbet.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.elitbet.service.impl.StatisticServiceImpl.parameterMap;

@Service
public class FootballMatchStatisticServiceImpl extends FindById<FootballMatchStatistic,FootballMatchStatisticRepository>
        implements FootballMatchStatisticService {
    @Autowired
    FootballMatchStatisticRepository footballMatchStatisticRepository;
    @Autowired
    StatisticService statisticService;
    @Autowired
    StatisticRepository statisticRepository;

    @Override
    public FootballMatchStatistic create(String parametersString) {
        FootballMatchStatistic footballMatchStatistic = new FootballMatchStatistic();
        Map<String,String> parameterMap = parameterMap(parametersString);
        footballMatchStatistic.setHomeName(parameterMap.get("home_name"));
        footballMatchStatistic.setAwayName(parameterMap.get("away_name"));
        footballMatchStatistic.setHomeGoals(0);
        footballMatchStatistic.setAwayGoals(0);
        footballMatchStatistic =  footballMatchStatisticRepository.save(footballMatchStatistic);
        System.out.println(footballMatchStatistic.getClass());
        return footballMatchStatistic;
    }

    @Override
    public FootballMatchStatistic update(Statistic statistic, String parametersString) {
        System.out.println("In football match update" + statistic.getClass());
        FootballMatchStatistic footballMatchStatistic = (FootballMatchStatistic) statistic;
        Map<String,String> parameterMap = parameterMap(parametersString);
        footballMatchStatistic.setHomeName(parameterMap.get("home_name"));
        footballMatchStatistic.setAwayName(parameterMap.get("away_name"));
        footballMatchStatistic.setHomeGoals(Integer.parseInt(parameterMap.get("home_goals")));
        footballMatchStatistic.setAwayGoals(Integer.parseInt(parameterMap.get("away_goals")));
        return footballMatchStatisticRepository.save(footballMatchStatistic);
    }
}
