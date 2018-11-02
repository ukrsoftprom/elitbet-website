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
    public static final String FIRST_WIN = "1";
    public static final String SECOND_WIN = "2";
    public static final String DRAW = "X";

    @Id
    @Column(name = "OUTCOME_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;
    @Column(name = "DESCRIPTION")
    private String description;
}