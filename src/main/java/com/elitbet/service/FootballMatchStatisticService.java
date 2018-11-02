package com.elitbet.service;

import com.elitbet.model.Statistic;

public interface FootballMatchStatisticService extends IStatistic {
    Statistic findById(Long id);
    Statistic create(String parameters);
    Statistic update(Statistic statistic, String parameters);
}