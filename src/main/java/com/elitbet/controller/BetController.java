package com.elitbet.controller;

import com.elitbet.model.Wager;
import com.elitbet.model.Client;
import com.elitbet.service.WagerService;
import com.elitbet.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/bets")
class BetController {
    @Autowired
    ClientService clientService;
    @Autowired
    WagerService wagerService;

    @GetMapping("/{id}")
    @ResponseBody
    public Wager findOne(@PathVariable("id") long id){
        return wagerService.findById(id);
    }

    @GetMapping()
    @ResponseBody
    public List<Wager> findAll(Authentication authentication){
        return wagerService.findAllByAuthenticatedUser(authentication);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Wager create(@RequestBody Wager wager){
        return wagerService.create(wager);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") long id, @RequestBody Wager wager){
        wagerService.update(wager);
    }

    @GetMapping(path = "/create")
    @ResponseBody
    public String createBet(
            Authentication authentication,
            @RequestParam("outcome_id") long outcomeId,
            @RequestParam("bet_value") double betValue){
        Client client = clientService.findByName(authentication.getName());
        Wager wager = wagerService.createBet(client,outcomeId,betValue);
        if (wager == null) {
            return "fuck out mother fucker";
        }
        return "Created wager: " + wager.getClient() + " to " + wager.getOutcome().getEvent().getDescription() + " " + wager.getBetValue();
    }

    // TODO: 29.10.2018 delete in new version of front-end
    @GetMapping(path = "/all")
    public ModelAndView all(Authentication authentication){
        List<Wager> wagers = wagerService.findAllByAuthenticatedUser(authentication);
        ModelAndView modelAndView = new ModelAndView("allbets");
        modelAndView.addObject("bets", wagers);
        modelAndView.addObject("username",authentication.getName());
        return modelAndView;
    }

}