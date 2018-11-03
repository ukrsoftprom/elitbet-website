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
            @RequestParam("parameters") String parametersString,
            @RequestParam("tournament") String tournament,
            @RequestParam("coefficients") String coefficientsString){

        System.out.println("create");
        eventService.updateOutcomeOdds(id,coefficientsString);
        eventService.create(id,eventType,time,parametersString,tournament,coefficientsString);
    }

    @GetMapping(path = "/update")
    @ResponseBody
    public void updateFootballMatch(
            @RequestParam("access_token") String accessToken,
            @RequestParam("id") String id,
            @RequestParam("start_timestamp") long time,
            @RequestParam("parameters") String parameters,
            @RequestParam("tournament") String tournament,
            @RequestParam(value = "status") String status){
        System.out.println("update");
        eventService.update(id,tournament,time,parameters,status);
    }

    @GetMapping(path = "/notstarted")
    @ResponseBody
    public List<Event> findAllNotStarted(){
        return eventService.findAllNotStarted();
    }
}