package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "EventResult")
@Setter @Getter @NoArgsConstructor
public class EventResult {
    @Id
    @Column(name="EventResultId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventResultId;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventId")
    private Event event;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BetTypeId")
    private BetType betType;
    @Column(name="Coefficient")
    private double coefficient;
    @Column(name="Result")
    private boolean result;

    @Override
    public String toString() {
        return "EventResult{" +
                "betType=" + betType +
                ", coefficient=" + coefficient +
                ", result=" + result +
                '}';
    }
}