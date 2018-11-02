package com.elitbet.service.impl;

import com.elitbet.model.EventStatus;
import com.elitbet.repository.EventStatusRepository;
import com.elitbet.service.EventStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventStatusServiceImpl implements EventStatusService {
    @Autowired
    EventStatusRepository eventStatusRepository;

    @Override
    public EventStatus findByDescription(String description) {
        return eventStatusRepository.findByDescriptionEquals(description);
    }
}
