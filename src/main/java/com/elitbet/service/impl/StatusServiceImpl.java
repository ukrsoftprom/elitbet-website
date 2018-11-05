package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.repository.*;
import com.elitbet.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    EventStatusRepository eventStatusRepository;
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Autowired
    WagerStatusRepository wagerStatusRepository;
    @Autowired
    OutcomeStatusRepository outcomeStatusRepository;
    @Autowired
    OutcomeTypeRepository outcomeTypeRepository;

    @Override
    public EventStatus getEventStatus(String description) {
        return eventStatusRepository.findByDescriptionEquals(description);
    }

    @Override
    public EventType getEventType(String description) {
        return eventTypeRepository.findByDescriptionEquals(description);
    }

    @Override
    public WagerStatus getWagerStatus(String description) {
       return wagerStatusRepository.findByDescriptionEquals(description);
    }

    @Override
    public OutcomeStatus getOutcomeStatus(String description) {
        return outcomeStatusRepository.findByDescriptionEquals(description);
    }

    @Override
    public OutcomeType getOutcomeType(String description) {
        return outcomeTypeRepository.findByDescriptionEquals(description);
    }
}