package com.elitbet.service;

import com.elitbet.model.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WagerService {
    List<Wager> findAllByOutcome(Outcome outcome);

    Wager createBet(Client client, long eventResultId, double betValue);

    Wager findById(Long betId);

    List<Wager> findAllByAuthenticatedUser(Authentication authentication);

    void calculateWager(Wager wager);
}
