package com.elitbet.service;

import com.elitbet.model.Event;
import com.elitbet.model.Participant;

public interface IParticipant {
    Participant create(Event event, String name);
    Participant update(Participant participant, String name, String statistic);
}
