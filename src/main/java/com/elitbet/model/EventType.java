package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "EventType")
@Setter @Getter @NoArgsConstructor @ToString
public class EventType {
    public static final String football_match = "Football Match";

    @Id
    @Column(name = "EventTypeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventTypeId;
    @Column(name="Description")
    private String description;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParticipantTypeId", referencedColumnName = "ParticipantTypeId")
    private ParticipantType participantType;

    @Override
    public String toString() {
        return description;
    }
}