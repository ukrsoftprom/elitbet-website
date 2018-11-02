package com.elitbet.service;

import com.elitbet.model.OutcomeStatus;

public interface OutcomeStatusService {
    OutcomeStatus findByDescription(String description);
}
