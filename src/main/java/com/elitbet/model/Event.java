package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "EVENT")
@Setter@Getter@NoArgsConstructor@ToString
public class Event {
    @Id
    @Column(name = "EVENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_TYPE_ID",referencedColumnName = "EVENT_TYPE_ID")
    private EventType eventType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_STATUS_ID",referencedColumnName = "EVENT_STATUS_ID")
    private EventStatus eventStatus;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STATISTIC_ID",referencedColumnName = "STATISTIC_ID")
    private Statistic statistic;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOURNAMENT_ID", referencedColumnName = "TOURNAMENT_ID")
    private Tournament tournament;
    @Column(name = "FLASHSCORE_ID")
    private String flashscoreId;
    @Column(name = "START_DATETIME")
    private Date startDateTime;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    private List<Outcome> outcomeList = new ArrayList<>();
}