package com.elitbet.controller;

import com.elitbet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/events")
class EventController {
    @Autowired
    EventService eventService;

    @GetMapping(path = "/create")
    @ResponseBody
    public String createFootballMatch(
            @RequestParam("access_token") String accessToken,
            @RequestParam("id") String id,
            @RequestParam("event_type") String eventType,
            @RequestParam("start_timestamp") long time,
            @RequestParam("names") String names,
            @RequestParam("tournament") String tournament,
            @RequestParam("coefficients") String coefficients){

        eventService.updateIfNotExistCreateEvent(id,eventType,time,names,tournament,coefficients);
        return "Creating";
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
}