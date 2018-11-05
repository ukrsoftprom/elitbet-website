package com.elitbet.controller;

import com.elitbet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/parser")
public class ParserController {
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
            @RequestParam("odds") String oddsString){

        eventService.updateOdds(id,oddsString);
        eventService.create(id,eventType,time,parametersString,tournament,oddsString);
    }

    @GetMapping(path = "/update")
    @ResponseBody
    public void update(
            @RequestParam("access_token") String accessToken,
            @RequestParam("id") String id,
            @RequestParam("start_timestamp") long time,
            @RequestParam("parameters") String parametersString,
            @RequestParam("tournament") String tournament,
            @RequestParam(value = "status") String status){

        eventService.update(id,tournament,time,parametersString,status);
    }
}
