package com.elitbet.service.impl;

import com.elitbet.model.EventType;
import com.elitbet.repository.EventTypeRepository;
import com.elitbet.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventTypeServiceImpl implements EventTypeService {
    @Autowired
    EventTypeRepository eventTypeRepository;

    @Override
    public EventType findByDescription(String description) {
        return eventTypeRepository.findByDescriptionEquals(description);
    }
}
