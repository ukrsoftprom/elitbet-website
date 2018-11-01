package com.elitbet.service;

import com.elitbet.model.BetType;
import com.elitbet.model.EventType;
import com.elitbet.model.Participant;

import java.util.List;

public interface OutcomeHandler {
    boolean execute(List<Participant> participantList, double parameter);
}
