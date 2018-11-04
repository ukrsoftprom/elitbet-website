package com.elitbet.controller;

import com.elitbet.model.Event;
import com.elitbet.model.Tournament;
import com.elitbet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.jws.WebParam;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(path = "/events")
class EventController {
    @Autowired
    EventService eventService;
    @Autowired
    TournamentService tournamentService;

    @GetMapping()
    public ModelAndView allEvents(@RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "20") int size){
        Page<Event> events = eventService.findAllNotStarted(page,size);
        Iterator<Event> eventIterator = events.iterator();
        List<Tournament> tournaments = tournamentService.findAll();
        ModelAndView mdl = new ModelAndView();
        mdl.setViewName("events");
        mdl.addObject("events", eventIterator);
        mdl.addObject("tournaments", tournaments);

        return mdl;
    }

}