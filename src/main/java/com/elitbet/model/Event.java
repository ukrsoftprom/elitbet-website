package com.elitbet.model;

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
@Table(name = "Event")
@Setter@Getter@NoArgsConstructor@ToString
public class Event {
    public static final String NOT_STARTED = "Not Started";
    public static final String STARTED = "Started";
    public static final String FINISHED = "Finished";
    public static final String POSTPONED = "Postponed";
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
    private List<Participant> participants = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    private List<EventResult> resultList = new ArrayList<>();
    @Column(name = "StartTime")
    private Date time;
    @Column(name = "EventStatus")
    private String status;

    public boolean notFinished() {
        return !status.equals(FINISHED);
    }

    public boolean notStarted() {
        return status.equals(NOT_STARTED);
    }

    public boolean notPostponed() {
        return !status.equals(POSTPONED);
    }

    public boolean isPostponed() {
        return status.equals(POSTPONED);
    }

    public void addParticipant(Participant participant){
        participants.add(participant);
    }

    public void addEventResult(EventResult eventResult){
        resultList.add(eventResult);
    }
}