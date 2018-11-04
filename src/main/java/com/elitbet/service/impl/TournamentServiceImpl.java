package com.elitbet.service.impl;

import com.elitbet.model.Tournament;
import com.elitbet.repository.TournamentRepository;
import com.elitbet.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {
    @Autowired
    TournamentRepository tournamentRepository;

    @Override
    public List<Tournament> findAll(){
        return tournamentRepository.findAll();
    }

    private Tournament create(String description) {
        Tournament tournament = new Tournament();
        tournament.setDescription(description);
        return tournamentRepository.save(tournament);
    }

    @Override
    public Tournament findByDescription(String description) {
        Tournament tournament = tournamentRepository.findByDescriptionEquals(description);
        if(tournament == null){
            tournament = create(description);
        }
        return tournament;
    }
}
