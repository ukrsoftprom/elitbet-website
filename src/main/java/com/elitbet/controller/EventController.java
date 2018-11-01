package com.elitbet.controller;

import com.elitbet.model.Event;
import com.elitbet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/events")
class EventController {
    @Autowired
    EventService eventService;

    @GetMapping(path = "/create")
    @ResponseBody
    public void createFootballMatch(
            @RequestParam("access_token") String accessToken,
            @RequestParam("id") String id,
            @RequestParam("event_type") String eventType,
            @RequestParam("start_timestamp") long time,
            @RequestParam("names") String nameString,
            @RequestParam("tournament") String tournament,
            @RequestParam("coefficients") String coefficientString){

        eventService.updateCoefficients(id,coefficientString);
        eventService.create(id,eventType,time,nameString,tournament,coefficientString);
    }

    @GetMapping(path = "/update")
    @ResponseBody
    public void updateFootballMatch(
            @RequestParam("access_token") String accessToken,
            @RequestParam("id") String id,
            @RequestParam("start_timestamp") long time,
            @RequestParam("names") String names,
            @RequestParam("tournament") String tournament,
            @RequestParam(value = "status") String status,
            @RequestParam(value = "results") String results){

        eventService.update(id,tournament,time,names,status,results);
    }

    @GetMapping(path = "/notstarted")
    @ResponseBody
    public List<Event> findAllNotStarted(){
        return eventService.findAllNotStarted();
    }
}