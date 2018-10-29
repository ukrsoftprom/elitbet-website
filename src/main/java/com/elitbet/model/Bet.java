package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Bet")
@Getter@Setter@NoArgsConstructor@ToString
public class Bet {
    public static final String no_status = "No Status";
    public static final String passed = "Passed";
    public static final String not_passed = "Not Passed";
    public static final String returned = "Returned";

    @Id
    @Column(name = "BetId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long betId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventResultId", referencedColumnName = "EventResultId")
    private EventResult eventResult;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private User user;
    @Column(name = "BetStatus")
    private String status;
    @Column(name = "Coefficient")
    private double coefficient;
    @Column(name = "BetValue")
    private double value;
}