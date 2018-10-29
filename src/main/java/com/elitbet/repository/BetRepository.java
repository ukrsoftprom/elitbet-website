package com.elitbet.repository;

import com.elitbet.model.Bet;
import com.elitbet.model.EventResult;
import com.elitbet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> getAllByUser(User user);
    List<Bet> getAllByEventResult(EventResult eventResult);
}
