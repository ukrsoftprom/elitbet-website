package com.elitbet.service;

import com.elitbet.model.EventType;
import org.springframework.stereotype.Service;

public interface EventTypeService {
    EventType findByDescription(String description);
}