package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "EVENT_STATUS")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventStatus{
    public static final String NOT_STARTED = "Not Started";
    public static final String STARTED = "Started";
    public static final String FINISHED = "Finished";
    public static final String POSTPONED = "Postponed";

    @Id
    @Column(name = "EVENT_STATUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    @Column(name="DESCRIPTION")
    String description;
}