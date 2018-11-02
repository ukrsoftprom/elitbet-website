package com.elitbet.repository;

import com.elitbet.model.WagerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WagerStatusRepository extends JpaRepository<WagerStatus, Long>,FindByDescription<WagerStatus> {}
