package com.elitbet.repository;

import com.elitbet.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
    Event getByFlashscoreIdEquals(String flashscoreId);

    List<Event> findAllByEventStatus_DescriptionAndTournament_TournamentId(
            String eventStatusDescription, Long tournamentId);

    List<Event> findAllByEventStatus_Description(String eventStatusDescription);

    Page<Event> findAllByEventStatus_DescriptionAndTournament_TournamentId(
            String eventStatusDescription, Long tournamentId, Pageable pageable);

    Page<Event> findAllByEventStatus_Description(String eventStatusDescription, Pageable pageable);
}