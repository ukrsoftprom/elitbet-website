package com.elitbet.service;

import com.elitbet.model.Tournament;

import java.util.List;

public interface TournamentService {
    List<Tournament> findAll();

    Tournament findByDescription(String description);
}
