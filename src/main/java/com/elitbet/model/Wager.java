package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "WAGER")
@Getter@Setter@NoArgsConstructor@ToString
public class Wager {
    @Id
    @Column(name = "WAGER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wagerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUTCOME_ID", referencedColumnName = "OUTCOME_ID")
    private Outcome outcome;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
    @OneToOne
    @JoinColumn(name = "WAGER_STATUS_ID",referencedColumnName = "WAGER_STATUS_ID")
    private WagerStatus wagerStatus;
    @Column(name = "ODDS")
    private double odds;
    @Column(name = "BET_VALUE")
    private double betValue;
}