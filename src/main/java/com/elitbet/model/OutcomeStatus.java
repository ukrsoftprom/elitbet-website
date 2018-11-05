package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "OUTCOME_STATUS")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class OutcomeStatus{
    @Id
    @Column(name = "OUTCOME_STATUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    @Column(name="DESCRIPTION")
    String description;
}