package com.elitbet.controller;

import com.elitbet.model.Bet;
import com.elitbet.model.User;
import com.elitbet.service.BetService;
import com.elitbet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/bets")
class BetController {
    @Autowired
    UserService userService;
    @Autowired
    BetService betService;

    @GetMapping("/{id}")
    @ResponseBody
    public Bet findOne(@PathVariable("id") long id){
        return betService.findById(id);
    }

    @GetMapping()
    @ResponseBody
    public List<Bet> findAll(Authentication authentication){
        return betService.findAllByAuthenticatedUser(authentication);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Bet create(@RequestBody Bet bet){
        return betService.create(bet);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") long id, @RequestBody Bet bet){
        betService.update(bet);
    }

    @GetMapping(path = "/create")
    @ResponseBody
    public String createBet(
            Authentication authentication,
            @RequestParam("event_result_id") long eventResultId,
            @RequestParam("bet_value") double betValue){
        User user = userService.findByUsername(authentication.getName());
        Bet bet = betService.createBet(user,eventResultId,betValue);
        if (bet == null) {
            return "fuck out mother fucker";
        }
        return "Created bet: " + bet.getUser() + " to " + bet.getEventResult().getEvent().getDescription() + " " + bet.getValue();
    }

    // TODO: 29.10.2018 delete in new version of front-end
    @GetMapping(path = "/all")
    public ModelAndView all(Authentication authentication){
        List<Bet> bets = betService.findAllByAuthenticatedUser(authentication);
        ModelAndView modelAndView = new ModelAndView("allbets");
        modelAndView.addObject("bets",bets);
        modelAndView.addObject("username",authentication.getName());
        return modelAndView;
    }

}