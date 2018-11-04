package com.elitbet.controller;

import com.elitbet.model.Tournament;
import com.elitbet.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TestController {
    @Autowired
    TournamentService tournamentService;

    @GetMapping("/test/tournament")
    @ResponseBody
    public void tournamentsWithQuantityMatches(){
        Map<Tournament,Integer> map = tournamentService.findTournamentsWithCurrentEvents();
        for(Tournament tournament: map.keySet()){
            int quantity = map.get(tournament);
            System.out.println(tournament.getDescription() +  " " +quantity);
        }
    }
}
