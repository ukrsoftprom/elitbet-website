package com.elitbet.service;

import com.elitbet.model.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BetService {
    List<Bet> findAllByEventResult(EventResult eventResult);
    Bet createBet(User user, long eventResultId, double betValue);
    Bet updateStatus(Bet bet, String description);
    Bet findById(Long betId);
    List<Bet> findAllByUser(User user);

    List<Bet> findAllByAuthenticatedUser(Authentication authentication);

    Bet calculateBet(Bet bet);

    Bet create(Bet bet);

    void update(Bet bet);
}
