package com.elitbet.repository;

import com.elitbet.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusRepository extends JpaRepository<EventStatus, Long>, FindByDescription<EventStatus> {}
