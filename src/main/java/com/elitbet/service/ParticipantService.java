package com.elitbet.service;

import com.elitbet.model.Event;
import com.elitbet.model.Participant;
import com.elitbet.model.ParticipantType;

public interface ParticipantService {
    Participant findById(Long participantId);
    Participant create(Event event, ParticipantType participantType, String name);
    Participant update(Participant participant, String name, String statistic);
}
