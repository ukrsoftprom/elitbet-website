package com.elitbet.service;

import com.elitbet.model.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WagerService {
    List<Wager> findAllByEventResult(Outcome outcome);
    Wager createBet(Client client, long eventResultId, double betValue);
    Wager updateStatus(Wager wager, String description);
    Wager findById(Long betId);
    List<Wager> findAllByUser(Client client);

    List<Wager> findAllByAuthenticatedUser(Authentication authentication);

    Wager calculateBet(Wager wager);

    Wager create(Wager wager);

    void update(Wager wager);
}
