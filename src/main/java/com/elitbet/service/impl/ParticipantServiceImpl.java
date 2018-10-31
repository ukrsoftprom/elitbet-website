package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.model.Participant;
import com.elitbet.repository.ParticipantRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.IParticipant;
import com.elitbet.service.ParticipantService;
import com.elitbet.util.ParticipantManager;
import org.springframework.stereotype.Service;

@Service
public class ParticipantServiceImpl extends FindById<Participant, ParticipantRepository>
        implements ParticipantService {

    @Override
    public Participant create(Event event, ParticipantType participantType, String participantName) {
        String participantDescription = event.getEventType().getParticipantType().getDescription();
        IParticipant service = ParticipantManager.getInstance().getService(participantDescription);
        return service.create(event, participantName);
    }

    @Override
    public Participant update(Participant participant, String participantName, String participantStatistics) {
        String participantDescription = participant.getEvent().getEventType().getParticipantType().getDescription();
        IParticipant service = ParticipantManager.getInstance().getService(participantDescription);
        return service.update(participant,participantName,participantStatistics);
    }
}