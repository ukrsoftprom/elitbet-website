package com.elitbet.service.impl;

import com.elitbet.model.Event;
import com.elitbet.model.EventStatus;
import com.elitbet.model.Tournament;
import com.elitbet.repository.TournamentRepository;
import com.elitbet.service.EventService;
import com.elitbet.service.FindById;
import com.elitbet.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Map<Tournament, Integer> findTournamentsWithCurrentEvents() {
        List<Tournament> allTournaments = findAll();
        Map<Tournament,Integer> tournamentsWithCurrentEvents = new LinkedHashMap<>();
        for(Tournament tournament: allTournaments){
            int quantity = tournament.getEventList().size();
            if(quantity!=0){
                tournamentsWithCurrentEvents.put(tournament,quantity);
            }
        }
        return tournamentsWithCurrentEvents;
    }

    @Override
    public List<Event> getCurrentEventsFromTournament(Long tournamentId){
        Tournament tournament = findById(tournamentId);
        List<Event> currentEventList = new ArrayList<>();
        for(Event event:tournament.getEventList()){
            System.out.println(event);
            if(event.getEventStatus().getDescription().equals(EventStatus.NOT_STARTED)){
                currentEventList.add(event);
            }
        }
        return currentEventList;
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
