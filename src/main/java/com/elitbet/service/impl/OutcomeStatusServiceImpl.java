package com.elitbet.service.impl;

import com.elitbet.model.OutcomeStatus;
import com.elitbet.repository.OutcomeStatusRepository;
import com.elitbet.service.OutcomeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutcomeStatusServiceImpl implements OutcomeStatusService {
    @Autowired
    OutcomeStatusRepository outcomeStatusRepository;

    @Override
    public OutcomeStatus findByDescription(String description) {
        return outcomeStatusRepository.findByDescriptionEquals(description);
    }
}
