package com.elitbet.controller;

import com.elitbet.model.Event;
import com.elitbet.model.Outcome;
import com.elitbet.model.Wager;
import com.elitbet.model.Client;
import com.elitbet.service.EventService;
import com.elitbet.service.OutcomeService;
import com.elitbet.service.WagerService;
import com.elitbet.service.ClientService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wagers")
class WagerController {
    @Autowired
    ClientService clientService;
    @Autowired
    WagerService wagerService;
    @Autowired
    OutcomeService outcomeService;

    @GetMapping(path = "/all")
    public ModelAndView all(Authentication authentication){
        List<Wager> wagers = wagerService.findAllByAuthenticatedUser(authentication);
        ModelAndView modelAndView = new ModelAndView("wagers");
        modelAndView.addObject("wagers", wagers);
        modelAndView.addObject("name",authentication.getName());
        return modelAndView;
    }

    @GetMapping(path = "/add")
    public ModelAndView betadd(@RequestParam(required = true) Long outcomeId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("events");
        Outcome outcome = outcomeService.findById(outcomeId);
        Event event = outcome.getEvent();
        Map<String, String> dataMap = event.getStatistic().getDataMap();
        modelAndView.addObject("outcome", outcome);
        modelAndView.addObject("event", event);
        modelAndView.addObject("dataMap", dataMap);
        return modelAndView;
    }

    @PostMapping(path = "/add")
    public ModelAndView betadd(@RequestParam Long outcomeId, @RequestParam Double betValue, Authentication authentication){
        Client client = clientService.findByName(authentication.getName());
        Wager wager = wagerService.createBet(client,outcomeId,betValue);
        ModelAndView modelAndView = new ModelAndView();

        if(wager == null){
            modelAndView.addObject("errorMessage","You have no money, bitcher");
            modelAndView.setViewName("error");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/wagers/all");
        return modelAndView;

    }

}