package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "WAGER_STATUS")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class WagerStatus {
    public static final String NO_STATUS = "NO STATUS";
    public static final String PASSED = "PASSED";
    public static final String NOT_PASSED = "NOT PASSED";
    public static final String RETURNED = "RETURNED";

    @Id
    @Column(name = "WAGER_STATUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    @Column(name = "DESCRIPTION")
    private String description;
}