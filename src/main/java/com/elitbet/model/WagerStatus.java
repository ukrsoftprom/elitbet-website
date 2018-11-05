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
    @Id
    @Column(name = "WAGER_STATUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    @Column(name = "DESCRIPTION")
    private String description;
}