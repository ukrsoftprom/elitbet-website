package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Event")
@Setter@Getter@NoArgsConstructor@ToString
public class Event {
    public static final String not_started = "Not Started";
    public static final String started = "Started";
    public static final String finished = "Finished";
    public static final String postponed = "Postponed";
    @Id
    @Column(name = "EventId")
    private String eventId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventTypeId",referencedColumnName = "EventTypeId")
    private EventType eventType;
    @Column(name="Description")
    private String description;
    @Column(name="Tournament")
    private String tournament;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    private List<Participant> participants;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    private List<EventResult> resultList;
    @Column(name = "StartTime")
    private Date time;
    @Column(name = "EventStatus")
    private String status;

    public boolean notStarted(){
        return status.equals(not_started);
    }

    public boolean isFinished() {
        return status.equals(finished);
    }

    public boolean isStarted() {
        return status.equals(started);
    }

    public boolean isPostponed() {
        return status.equals(postponed);
    }
}