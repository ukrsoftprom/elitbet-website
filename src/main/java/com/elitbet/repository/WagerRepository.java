package com.elitbet.repository;

import com.elitbet.model.Wager;
import com.elitbet.model.Outcome;
import com.elitbet.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WagerRepository extends JpaRepository<Wager, Long> {
    List<Wager> getAllByClient(Client client);
    List<Wager> getAllByOutcome(Outcome outcome);
}
