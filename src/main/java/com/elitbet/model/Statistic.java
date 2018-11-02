package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name="STATISTIC")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "STATISTIC")
@Setter
@Getter
@NoArgsConstructor
@ToString
public abstract class Statistic {
    @Id
    @Column(name="STATISTIC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statisticId;

    public abstract String names();
}
