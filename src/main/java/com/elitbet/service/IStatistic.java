package com.elitbet.service;

import com.elitbet.model.Statistic;

public interface IStatistic {
    Statistic create(String parameters);
    Statistic update(Statistic statistic , String parameters);
}
