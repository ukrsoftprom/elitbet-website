package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.model.Participant;
import com.elitbet.repository.ParticipantRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.IParticipant;
import com.elitbet.service.ParticipantService;
import com.elitbet.util.ParticipantServiceManager;
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
        Class serviceClass = ParticipantServiceManager.getInstance().getServiceName(participantDescription);
        IParticipant serviceBean = (IParticipant) context.getBean(serviceClass);
        return serviceBean.create(event, participantName);
    }

    @Override
    public Participant update(Participant participant, String participantName, String participantStatistics) {
        String participantDescription = participant.getEvent().getEventType().getParticipantType().getDescription();
        Class serviceClass = ParticipantServiceManager.getInstance().getServiceName(participantDescription);
        IParticipant serviceBean = (IParticipant) context.getBean(serviceClass);
        return serviceBean.update(participant,participantName,participantStatistics);
    }
}