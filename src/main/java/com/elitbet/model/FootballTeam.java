package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="FootballTeam")
@PrimaryKeyJoinColumn(name = "ParticipantId")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class FootballTeam extends Participant{
    @Column(name="ParticipantId")
    private Long participantId;
    @Column(name="Name")
    private String name;
    @Column(name="Goals")
    private int goals;

    @Override
    public String toString() {
        return name;
    }
}
