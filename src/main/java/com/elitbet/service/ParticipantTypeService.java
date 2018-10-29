package com.elitbet.service;

import com.elitbet.model.ParticipantType;

public interface ParticipantTypeService {
    ParticipantType findById(Long id);
    ParticipantType findByDescription(String description);
}
