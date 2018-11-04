package com.elitbet.repository;

import com.elitbet.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament findByDescriptionEquals(String description);
}
