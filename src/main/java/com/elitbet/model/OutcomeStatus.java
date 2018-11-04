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
    public static final String NO_STATUS = "No status";
    public static final String PASSED = "Passed";
    public static final String NOT_PASSED = "Not passed";
    public static final String RETURNED = "Returned";

    @Id
    @Column(name = "OUTCOME_STATUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    @Column(name="DESCRIPTION")
    String description;
}