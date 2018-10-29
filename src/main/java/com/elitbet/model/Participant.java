package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity(name="Participant")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Participant")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Participant {
    @Id
    @Column(name="ParticipantId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantId;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventId")
    private Event event;
}