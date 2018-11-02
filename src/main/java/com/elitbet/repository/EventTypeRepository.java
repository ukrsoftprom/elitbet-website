package com.elitbet.repository;

import com.elitbet.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType,Long>,FindByDescription<EventType> { }