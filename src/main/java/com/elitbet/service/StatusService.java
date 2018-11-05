package com.elitbet.service;

import com.elitbet.model.*;

public interface StatusService {
    EventStatus getEventStatus(String description);
    EventType getEventType(String description);
    WagerStatus getWagerStatus(String description);
    OutcomeStatus getOutcomeStatus(String description);
    OutcomeType getOutcomeType(String description);
}