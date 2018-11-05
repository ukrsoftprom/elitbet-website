package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "OUTCOME_TYPE")
@Setter@Getter@NoArgsConstructor@ToString
public class OutcomeType {
    @Id
    @Column(name = "OUTCOME_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;
    @Column(name = "DESCRIPTION")
    private String description;
}