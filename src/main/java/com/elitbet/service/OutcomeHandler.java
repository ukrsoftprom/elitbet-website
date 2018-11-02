package com.elitbet.service;

import com.elitbet.model.Statistic;

public interface OutcomeHandler {
    boolean execute(Statistic statistic, String parameters);
}
