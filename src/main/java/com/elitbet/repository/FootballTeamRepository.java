package com.elitbet.repository;

import com.elitbet.model.FootballTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballTeamRepository extends JpaRepository<FootballTeam,Long> {
}
