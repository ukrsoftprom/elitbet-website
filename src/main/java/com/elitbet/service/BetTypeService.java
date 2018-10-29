package com.elitbet.service;

import com.elitbet.model.BetType;
import com.elitbet.model.ParticipantType;
import org.springframework.stereotype.Service;

@Service
public interface BetTypeService {
    BetType findById(Long betTypeId);
    BetType findByDescription(String description);
}
