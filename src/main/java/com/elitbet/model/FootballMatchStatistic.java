package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="FOOTBALL_MATCH_STATISTIC")
@PrimaryKeyJoinColumn(name = "STATISTIC_ID")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FootballMatchStatistic extends Statistic {
//    @Column(name="STATISTIC_ID")
//    private Long statisticId;
    @Column(name="HOME_NAME")
    private String homeName;
    @Column(name="AWAY_NAME")
    private String awayName;
    @Column(name="HOME_GOALS")
    private int homeGoals;
    @Column(name="AWAY_GOALS")
    private int awayGoals;

    public String names(){
        return homeName + ":" + awayName;
    }
}