package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity(name= "FOOTBALL_MATCH_STATISTIC")
@Table(name="FOOTBALL_MATCH_STATISTIC")
@PrimaryKeyJoinColumn(name = "STATISTIC_ID")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FootballMatchStatistic extends Statistic {
    @Column(name="HOME_NAME")
    private String homeName;
    @Column(name="AWAY_NAME")
    private String awayName;
    @Column(name="HOME_GOALS")
    private int homeGoals;
    @Column(name="AWAY_GOALS")
    private int awayGoals;

    @Override
    public String names(){
        return homeName + " : " + awayName;
    }

    @Override
    public Map<String, String> getDataMap() {
        Map<String,String> dataMap = new LinkedHashMap<>();
        dataMap.put("home_name",homeName);
        dataMap.put("away_name",awayName);
        dataMap.put("home_goals", String.valueOf(homeGoals));
        dataMap.put("away_goals", String.valueOf(awayGoals));
        return dataMap;
    }
}