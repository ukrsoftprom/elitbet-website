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
    StatusService statusService;
    @Autowired
    EventService eventService;
    @Autowired
    OutcomeService outcomeService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientBankService clientBankService;

    @Override
    public List<Wager> findAllByAuthenticatedUser(Authentication authentication) {
        Client client = clientService.findByName(authentication.getName());
        return wagerRepository.getAllByClient(client);
    }

    @Override
    public List<Wager> findAllByOutcome(Outcome outcome){
        return wagerRepository.getAllByOutcome(outcome);
    }

    @Override
    public Wager createBet(Client client, long eventResultId, double betValue) {
        Wager wager = null;
        Outcome outcome = outcomeService.findById(eventResultId);
        if(isValidWager(outcome.getEvent(),client.getClientBank(),betValue)) {
            wager = new Wager();
            wager.setClient(client);
            wager.setWagerStatus(statusService.getWagerStatus(Status.WAGER_NO_STATUS));
            wager.setOutcome(outcome);
            wager.setOdds(outcome.getOdds());
            wager.setBetValue(betValue);
            clientBankService.updateBankValue(client.getClientBank(), -betValue);
            wagerRepository.save(wager);
        }
        return wager;
    }

    @Override
    public void calculateWager(Wager wager) {
        String status = wager.getOutcome().getOutcomeStatus().getDescription();
        ClientBank clientBank = wager.getClient().getClientBank();
        updateStatus(wager,status);
        clientBankService.updateBankValue(clientBank,updateValue(wager,status));
        wagerRepository.save(wager);
    }

    private void updateStatus(Wager wager, String description) {
        wager.setWagerStatus(statusService.getWagerStatus(description));
        wagerRepository.save(wager);
    }

    private double updateValue(Wager wager, String outcomeStatus){
        switch (outcomeStatus){
            case Status.OUTCOME_PASSED: return wager.getBetValue() * wager.getOdds();
            case Status.OUTCOME_RETURNED: return wager.getBetValue();
            case Status.OUTCOME_NOT_PASSED: return 0;
            default: return 0;
        }
    }

    // TODO: 05.11.2018 validator
    private boolean isValidWager(Event event, ClientBank clientBank, double betValue){
        String eventStatus = event.getEventStatus().getDescription();
        return eventStatus.equals(Status.EVENT_NOT_STARTED) && (betValue <= clientBank.getBankValue());
    }
}