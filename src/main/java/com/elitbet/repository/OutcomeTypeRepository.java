package com.elitbet.repository;

import com.elitbet.model.OutcomeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutcomeTypeRepository extends JpaRepository<OutcomeType, Long>,FindByDescription<OutcomeType> { }
