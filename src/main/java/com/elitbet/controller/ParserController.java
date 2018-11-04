package com.elitbet.controller;

import com.elitbet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ParserController {
    @Autowired
    EventService eventService;

    // TODO: 04.11.2018 change url
    @GetMapping(path = "/events/create")
    @ResponseBody
    public void createFootballMatch(
            @RequestParam("access_token") String accessToken,
            @RequestParam("id") String id,
            @RequestParam("event_type") String eventType,
            @RequestParam("start_timestamp") long time,
            @RequestParam("parameters") String parametersString,
            @RequestParam("tournament") String tournament,
            @RequestParam("coefficients") String coefficientsString){

        eventService.updateOdds(id,coefficientsString);
        eventService.create(id,eventType,time,parametersString,tournament,coefficientsString);
    }

    // TODO: 04.11.2018 change url
    @GetMapping(path = "/events/update")
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

    // TODO: 04.11.2018 API 0.3 common update method
    @GetMapping(path = "/updatenew")
    @ResponseBody
    public void update(
            @RequestParam("access_token") String accessToken,
            @RequestParam("id") String id,
            @RequestParam("start_timestamp") long time,
            @RequestParam("parameters") String parametersString,
            @RequestParam("tournament") String tournament,
            @RequestParam("status") String status,
            @RequestParam("coefficients") String coefficientsString,
            @RequestParam("event_type") String eventType){

        eventService.update(id,tournament,time,parametersString,status,coefficientsString,eventType);
    }
}
