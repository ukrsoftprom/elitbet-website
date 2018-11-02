package com.elitbet.service;

import com.elitbet.model.EventStatus;

public interface EventStatusService {
    EventStatus findByDescription(String description);
}
