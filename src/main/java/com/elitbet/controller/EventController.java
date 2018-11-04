package com.elitbet.controller;

import com.elitbet.model.Event;
import com.elitbet.model.Tournament;
import com.elitbet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/events")
class EventController {
    @Autowired
    EventService eventService;
    @Autowired
    TournamentService tournamentService;

    @GetMapping()
    public ModelAndView allEvents(
            @RequestParam(required = false) Long tournamentId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size, HttpSession session){

        Page<Event> events;
        if(tournamentId!=null){
            events = eventService.findAllNotStartedFromTournament(tournamentId, page, size);
        }else{
            events = eventService.findAllNotStarted(page,size);
        }

        List<Event> eventList = events.getContent();
        ModelAndView mdl = new ModelAndView();
        Map<Tournament,Integer> tournaments = tournamentService.findTournamentsWithCurrentEvents();
        session.setAttribute("events",eventList);
        session.setAttribute("tournaments", tournaments);
        return mdl;
    }

}