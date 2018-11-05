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
    @Id
    @Column(name = "EVENT_STATUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    @Column(name="DESCRIPTION")
    String description;
}