package com.elitbet.repository;

import com.elitbet.model.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutcomeRepository extends JpaRepository<Outcome,Long> {
}