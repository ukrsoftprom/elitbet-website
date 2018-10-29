package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "ParticipantType")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class ParticipantType {
    public static final String football_team = "Football Team";
    @Id
    @Column(name="ParticipantTypeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantTypeId;
    @Column(name="Description")
    private String description;
}
