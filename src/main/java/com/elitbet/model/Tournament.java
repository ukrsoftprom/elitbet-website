package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
}