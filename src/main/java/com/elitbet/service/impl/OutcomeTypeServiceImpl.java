package com.elitbet.service.impl;

import com.elitbet.model.OutcomeType;
import com.elitbet.repository.OutcomeTypeRepository;
import com.elitbet.service.OutcomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutcomeTypeServiceImpl implements OutcomeTypeService {
    @Autowired
    OutcomeTypeRepository outcomeTypeRepository;

    @Override
    public OutcomeType findByDescription(String description) {
        return outcomeTypeRepository.findByDescriptionEquals(description);
    }
}
