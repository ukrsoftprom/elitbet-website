package com.elitbet.service;

import com.elitbet.model.EventType;
import com.elitbet.model.Statistic;

public interface StatisticService {
    Statistic findById(Long statisticId);
    Statistic create(EventType eventType, String parameters);
    Statistic update(EventType eventType, Statistic statistic, String parameters);
}
