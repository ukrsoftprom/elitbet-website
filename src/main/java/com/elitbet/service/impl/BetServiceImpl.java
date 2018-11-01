package com.elitbet.service.impl;

import com.elitbet.model.Bet;
import com.elitbet.model.EventResult;
import com.elitbet.model.User;
import com.elitbet.repository.BetRepository;
import com.elitbet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetServiceImpl extends FindById<Bet,BetRepository> implements BetService {
    @Autowired
    BetRepository betRepository;
    @Autowired
    EventService eventService;
    @Autowired
    EventResultService eventResultService;
    @Autowired
    UserService userService;
    @Autowired
    UserBankService userBankService;

    @Override
    public List<Bet> findAllByEventResult(EventResult eventResult){
        return betRepository.getAllByEventResult(eventResult);
    }

    @Override
    public Bet createBet(User user, long eventResultId, double betValue) {
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setStatus(Bet.no_status);
        EventResult eventResult = eventResultService.findById(eventResultId);
        if (!eventResult.getEvent().notStarted()|| betValue>user.getUserBank().getBankValue()){
            System.out.println("Event has STARTED or you haven`t enough money");
            return null;
        }
        bet.setEventResult(eventResult);
        bet.setCoefficient(eventResult.getCoefficient());
        bet.setValue(betValue);
        userBankService.updateBankValue(bet.getUser().getUserBank(),-betValue);
        return betRepository.save(bet);
    }

    @Override
    public Bet updateStatus(Bet bet, String betStatus) {
        bet.setStatus(betStatus);
        return betRepository.save(bet);
    }


    @Override
    public List<Bet> findAllByUser(User user) {
        return betRepository.getAllByUser(user);
    }

    @Override
    public List<Bet> findAllByAuthenticatedUser(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return findAllByUser(user);
    }

    @Override
    public Bet calculateBet(Bet bet) {
        Boolean result = bet.getEventResult().isResult();
        if(bet.getEventResult().getEvent().isPostponed()){
            updateStatus(bet, Bet.returned);
            userBankService.updateBankValue(bet.getUser().getUserBank(), bet.getValue());
        } else if(result){
            updateStatus(bet, Bet.passed);
            double updateValue = bet.getValue() * bet.getCoefficient();
            userBankService.updateBankValue(bet.getUser().getUserBank(), updateValue);
        }else {
            updateStatus(bet, Bet.not_passed);
        }
        return betRepository.save(bet);
    }

    @Override
    public Bet create(Bet bet) {
        return betRepository.save(bet);
    }

    @Override
    public void update(Bet bet) {
        betRepository.save(bet);
    }
}