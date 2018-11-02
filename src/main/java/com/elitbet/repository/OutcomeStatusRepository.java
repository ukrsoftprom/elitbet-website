package com.elitbet.repository;

import com.elitbet.model.OutcomeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutcomeStatusRepository extends JpaRepository<OutcomeStatus,Long>,FindByDescription<OutcomeStatus> {}
