package com.elitbet.service.impl;

import com.elitbet.model.ParticipantType;
import com.elitbet.repository.ParticipantTypeRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.ParticipantTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantTypeServiceImpl
        extends FindById<ParticipantType, ParticipantTypeRepository>
        implements ParticipantTypeService {
    @Autowired
    ParticipantTypeRepository participantTypeRepository;

    @Override
    public ParticipantType findByDescription(String description) {
        return participantTypeRepository.findByDescriptionEquals(description);
    }
}