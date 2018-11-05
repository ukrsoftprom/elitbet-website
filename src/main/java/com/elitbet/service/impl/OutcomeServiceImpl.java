package com.elitbet.service.impl;

import com.elitbet.model.*;
import com.elitbet.repository.OutcomeRepository;
import com.elitbet.service.*;
import com.elitbet.util.OutcomeHandlerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutcomeServiceImpl extends FindById<Outcome,OutcomeRepository>
        implements OutcomeService {
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    WagerService wagerService;
    @Autowired
    StatusService statusService;

    @Override
    public Outcome createOutcome(Event event, OutcomeType outcomeType, double odds) {
        Outcome outcome = new Outcome();
        outcome.setEvent(event);
        // TODO: 02.11.2018 update in version outcomes with params
        outcome.setParameters("");
        outcome.setOutcomeType(outcomeType);
        outcome.setOutcomeStatus(statusService.getOutcomeStatus(Status.OUTCOME_NO_STATUS));
        outcome.setOdds(odds);
        return outcomeRepository.save(outcome);
    }

    @Override
    public void updateOdds(Outcome outcome, double odds) {
        outcome.setOdds(odds);
        outcomeRepository.save(outcome);
    }

    @Override
    public void updateOutcomeResult(Outcome outcome) {
        Event event = outcome.getEvent();
        String result = OutcomeHandlerManager.getInstance().result(
                outcome.getOutcomeType().getDescription(),
                event.getEventType().getDescription(),
                event.getStatistic(),
                outcome.getParameters());
        outcome.setOutcomeStatus(statusService.getOutcomeStatus(result));
        outcomeRepository.save(outcome);
        calculateWagers(outcome);
    }

    @Override
    public void calculateWagers(Outcome outcome) {
        for(Wager wager : wagerService.findAllByOutcome(outcome)){
            wagerService.calculateWager(wager);
        }
    }
}