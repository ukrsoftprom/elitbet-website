package com.elitbet.repository;

import com.elitbet.model.BetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetTypeRepository extends JpaRepository<BetType, Long> {
    BetType findAllByDescriptionEquals(String description);
}
