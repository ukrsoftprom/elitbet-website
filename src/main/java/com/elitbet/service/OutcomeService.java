package com.elitbet.service;

import com.elitbet.model.OutcomeType;
import com.elitbet.model.Event;
import com.elitbet.model.Outcome;
import org.springframework.stereotype.Service;

@Service
public interface OutcomeService {
    Outcome findById(Long EventResultId);
    Outcome createOutcome(Event event, OutcomeType outcomeType, double odds);
    Outcome updateOdds(Outcome outcome, double odds);

    Outcome updateOutcomeResult(Outcome outcome);

    void notifyResult(Outcome outcome);
}
