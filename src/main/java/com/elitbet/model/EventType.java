package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "EVENT_TYPE")
@Setter @Getter @NoArgsConstructor @ToString
public class EventType {
    public static final String FOOTBALL_MATCH = "Football Match";
    @Id
    @Column(name = "EVENT_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;
    @Column(name="DESCRIPTION")
    private String description;
}