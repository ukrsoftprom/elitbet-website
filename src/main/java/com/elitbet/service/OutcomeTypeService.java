package com.elitbet.service;

import com.elitbet.model.OutcomeType;
import org.springframework.stereotype.Service;

@Service
public interface OutcomeTypeService {
    OutcomeType findByDescription(String description);
}
