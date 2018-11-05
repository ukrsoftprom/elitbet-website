package com.elitbet.service;

import com.elitbet.model.Statistic;

public interface OutcomeHandler {
    String execute(Statistic statistic, String parameters);
}
