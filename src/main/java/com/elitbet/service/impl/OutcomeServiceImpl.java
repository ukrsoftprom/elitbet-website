package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.repository.OutcomeRepository;
import com.elitbet.service.OutcomeStatusService;
import com.elitbet.service.WagerService;
import com.elitbet.service.OutcomeService;
import com.elitbet.service.FindById;
import com.elitbet.util.OutcomeHandlerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutcomeServiceImpl extends FindById<Outcome,OutcomeRepository>
        implements OutcomeService {
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    OutcomeStatusService outcomeStatusService;
    @Autowired
    WagerService wagerService;

    @Override
    public Outcome createOutcome(Event event, OutcomeType outcomeType, double odds) {
        Outcome outcome = new Outcome();
        outcome.setEvent(event);
        // TODO: 02.11.2018 update in new version
        outcome.setParameters("");
        outcome.setOutcomeType(outcomeType);
        outcome.setOutcomeStatus(outcomeStatusService.findByDescription(OutcomeStatus.NO_STATUS));
        outcome.setOdds(odds);
        return outcomeRepository.save(outcome);
    }

    @Override
    public Outcome updateOdds(Outcome outcome, double odds) {
        outcome.setOdds(odds);
        return outcomeRepository.save(outcome);
    }

    @Override
    public Outcome updateOutcomeResult(Outcome outcome) {
        Event event = outcome.getEvent();
        boolean result = OutcomeHandlerManager.getInstance().result(
                outcome.getOutcomeType().getDescription(),
                event.getEventType().getDescription(),
                event.getStatistic(),
                outcome.getParameters());
        updateOutcomeStatus(outcome,result);
        return outcome;
    }

    // TODO: 02.11.2018 change
    private void updateOutcomeStatus(Outcome outcome, boolean result) {
        if(result){
            outcome.setOutcomeStatus(outcomeStatusService.findByDescription(OutcomeStatus.PASSED));
        }else {
            outcome.setOutcomeStatus(outcomeStatusService.findByDescription(OutcomeStatus.NOT_PASSED));
        }
        outcomeRepository.save(outcome);
    }

    @Override
    public void notifyResult(Outcome outcome) {
        for(Wager wager : wagerService.findAllByEventResult(outcome)){
            wagerService.calculateBet(wager);
        }
    }
}