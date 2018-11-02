package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "OUTCOME")
@Setter @Getter @NoArgsConstructor
public class Outcome {
    @Id
    @Column(name="OUTCOME_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outcomeId;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID")
    private Event event;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUTCOME_TYPE_ID",referencedColumnName = "OUTCOME_TYPE_ID")
    private OutcomeType outcomeType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUTCOME_STATUS_ID",referencedColumnName = "OUTCOME_STATUS_ID")
    private OutcomeStatus outcomeStatus;
    @Column(name = "PARAMETERS")
    private String parameters;
    @Column(name="ODDS")
    private double odds;
}