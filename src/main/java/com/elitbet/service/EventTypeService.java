package com.elitbet.service;

import com.elitbet.model.EventType;
import org.springframework.stereotype.Service;

public interface EventTypeService {
    EventType findById(Long EventTypeId);
    EventType findByDescription(String description);
}
