package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TOURNAMENT")
@Setter @Getter @NoArgsConstructor @ToString
public class Tournament {
    @Id
    @Column(name = "TOURNAMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;
    @Column(name="DESCRIPTION")
    private String description;
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament")
    private List<Event> eventList = new ArrayList<>();

    public void addEvent(Event event){
        eventList.add(event);
    }

}