package com.elitbet.repository;

import com.elitbet.model.ParticipantType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantTypeRepository extends JpaRepository<ParticipantType, Long> {
    ParticipantType findByDescriptionEquals(String description);
}
