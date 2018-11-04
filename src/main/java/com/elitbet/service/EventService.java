package com.elitbet.service;

import com.elitbet.model.Event;
import com.elitbet.model.Statistic;
import com.elitbet.model.Tournament;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {

    Event findByFlashscoreId(String id);

    List<Event> findAllNotStarted();

    List<Event> findAllNotStartedFromTournament(Long tournamentId);

    Page<Event> findAllNotStarted(int page, int size);

    Page<Event> findAllNotStartedFromTournament(Long tournamentId, int page, int size);

    void updateOdds(String id, String oddsString);

    Event create(String id, String eventType, long time, String names, String tournament, String odds);

    Event update(String id, String tournament, long time, String parameters, String statusFlashscore);

    Event update(String id, String tournament, long time, String parametersString, String status, String coefficientsString, String eventType);
}