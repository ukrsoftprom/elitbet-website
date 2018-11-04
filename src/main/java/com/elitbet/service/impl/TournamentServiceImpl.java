package com.elitbet.service.impl;

import com.elitbet.model.Event;
import com.elitbet.model.Tournament;
import com.elitbet.repository.TournamentRepository;
import com.elitbet.service.EventService;
import com.elitbet.service.FindById;
import com.elitbet.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TournamentServiceImpl extends FindById<Tournament,TournamentRepository> implements TournamentService {
    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    EventService eventService;

    @Override
    public List<Tournament> findAll(){
        return tournamentRepository.findAll();
    }

    @Override
    public Map<Tournament, Integer> findTournamentsWithCurrentMatches() {
        List<Event> currentEvents = eventService.findAllNotStarted();
        Map<Tournament,Integer> allTournaments = new HashMap<>();
        for(Tournament tournament: findAll()){
            allTournaments.put(tournament,0);
        }
        for(Event event: currentEvents){
            Tournament tournament = event.getTournament();
            int tournamentCurrentMatches = allTournaments.get(tournament);
            allTournaments.put(tournament,tournamentCurrentMatches+1);
        }
        Map<Tournament,Integer> tournamentsWithCurrentMatches = new HashMap<>();
        for(Tournament tournament: allTournaments.keySet()){
            int tournamentCurrentMatches = allTournaments.get(tournament);
            if(tournamentCurrentMatches!=0){
                tournamentsWithCurrentMatches.put(tournament,tournamentCurrentMatches);
            }
        }
        return tournamentsWithCurrentMatches;
    }

    @Override
    public Tournament findByDescription(String description) {
        Tournament tournament = tournamentRepository.findByDescriptionEquals(description);
        if(tournament == null){
            tournament = create(description);
        }
        return tournament;
    }

    private Tournament create(String description) {
        Tournament tournament = new Tournament();
        tournament.setDescription(description);
        return tournamentRepository.save(tournament);
    }


}
