package com.elitbet.controller;

import com.elitbet.model.Statistic;
import com.elitbet.model.Tournament;
import com.elitbet.service.StatisticService;
import com.elitbet.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TestController {
    @Autowired
    TournamentService tournamentService;
    @Autowired
    StatisticService statisticService;

    @GetMapping("/test/tournament")
    @ResponseBody
    public void tournamentsWithQuantityMatches(){
        Map<Tournament,Integer> map = tournamentService.findTournamentsWithCurrentEvents();
        for(Tournament tournament: map.keySet()){
            int quantity = map.get(tournament);
            System.out.println(tournament.getDescription() +  " " +quantity);
        }
    }

    @GetMapping("/test/getdatamap")
    @ResponseBody
    public void getDataMap(@RequestParam(name="id") long id){
        Statistic statistic = statisticService.findById( id);
        System.out.println(statistic.getDataMap());
    }

    @GetMapping("/test/geteventlist")
    @ResponseBody
    public void getCurrentEventList(@RequestParam(name="id") long id){
        System.out.println(tournamentService.getCurrentEventsFromTournament(id));
    }
}
