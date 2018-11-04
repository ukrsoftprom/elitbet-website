package com.elitbet.service;

import com.elitbet.model.Tournament;

import java.util.List;
import java.util.Map;

public interface TournamentService {
    Tournament findById(Long tournamentId);

    List<Tournament> findAll();

    Map<Tournament,Integer> findTournamentsWithCurrentMatches();

    Tournament findByDescription(String description);
}
