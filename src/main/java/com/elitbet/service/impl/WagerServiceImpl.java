package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.repository.WagerRepository;
import com.elitbet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WagerServiceImpl extends FindById<Wager,WagerRepository> implements WagerService {
    @Autowired
    WagerRepository wagerRepository;
    @Autowired
    WagerStatusService wagerStatusService;
    @Autowired
    EventService eventService;
    @Autowired
    OutcomeService outcomeService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientBankService clientBankService;

    @Override
    public List<Wager> findAllByEventResult(Outcome outcome){
        return wagerRepository.getAllByOutcome(outcome);
    }

    @Override
    public Wager createBet(Client client, long eventResultId, double betValue) {
        Wager wager = new Wager();
        wager.setClient(client);
        WagerStatus noStatus = wagerStatusService.findByDescription(WagerStatus.NO_STATUS);
        wager.setWagerStatus(noStatus);
        Outcome outcome = outcomeService.findById(eventResultId);
        // TODO: 02.11.2018 Bet Validator
        if (!outcome.getEvent().notStarted()|| betValue> client.getClientBank().getBankValue()){
            System.out.println("Event has STARTED or you haven`t enough money");
            return null;
        }
        wager.setOutcome(outcome);
        wager.setOdds(outcome.getOdds());
        wager.setBetValue(betValue);
        clientBankService.updateBankValue(wager.getClient().getClientBank(),-betValue);
        return wagerRepository.save(wager);
    }

    @Override
    public Wager updateStatus(Wager wager, String description) {
        wager.setWagerStatus(wagerStatusService.findByDescription(description));
        return wagerRepository.save(wager);
    }


    @Override
    public List<Wager> findAllByUser(Client client) {
        return wagerRepository.getAllByClient(client);
    }

    @Override
    public List<Wager> findAllByAuthenticatedUser(Authentication authentication) {
        Client client = clientService.findByName(authentication.getName());
        return findAllByUser(client);
    }

    @Override
    public Wager calculateBet(Wager wager) {
        OutcomeStatus outcomeStatus = wager.getOutcome().getOutcomeStatus();
        switch (outcomeStatus.getDescription()) {
            case OutcomeStatus.RETURNED:
                updateStatus(wager, WagerStatus.RETURNED);
                clientBankService.updateBankValue(wager.getClient().getClientBank(), wager.getBetValue());
                break;
            case OutcomeStatus.PASSED:
                updateStatus(wager, WagerStatus.PASSED);
                double updateValue = wager.getBetValue() * wager.getOdds();
                clientBankService.updateBankValue(wager.getClient().getClientBank(), updateValue);
                break;
            default:
                updateStatus(wager, WagerStatus.NOT_PASSED);
                break;
        }
        return wagerRepository.save(wager);
    }

    @Override
    public Wager create(Wager wager) {
        return wagerRepository.save(wager);
    }

    @Override
    public void update(Wager wager) {
        wagerRepository.save(wager);
    }
}