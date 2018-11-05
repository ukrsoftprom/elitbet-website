package com.elitbet.service;

import com.elitbet.model.OutcomeType;
import com.elitbet.model.Event;
import com.elitbet.model.Outcome;
import org.springframework.stereotype.Service;

@Service
public interface OutcomeService {
    Outcome findById(Long EventResultId);
    Outcome createOutcome(Event event, OutcomeType outcomeType, double odds);
    void updateOdds(Outcome outcome, double odds);

    void updateOutcomeResult(Outcome outcome);

    void calculateWagers(Outcome outcome);
}
