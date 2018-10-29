package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.model.Participant;
import com.elitbet.repository.ParticipantRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.IParticipant;
import com.elitbet.service.ParticipantService;
import com.elitbet.util.ParticipantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ParticipantServiceImpl extends FindById<Participant, ParticipantRepository>
        implements ParticipantService {
    @Autowired
    ApplicationContext context;

    @Override
    public Participant create(Event event, ParticipantType participantType, String participantName) {
        String participantDescription = event.getEventType().getParticipantType().getDescription();
        Class participantClass = ParticipantManager.getInstance().getService(participantDescription);
        IParticipant aClass = (IParticipant) context.getBean(participantClass);
        return aClass.create(event, participantName);
    }

    @Override
    public Participant update(Participant participant, String name, String statistic) {
        String participantDescription = participant.getEvent().getEventType().getParticipantType().getDescription();
        Class participantClass = ParticipantManager.getInstance().getService(participantDescription);
        IParticipant aClass = (IParticipant) context.getBean(participantClass);
        return aClass.update(participant,name,statistic);
    }
}