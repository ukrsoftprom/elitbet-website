package com.elitbet.util;

import com.elitbet.model.OutcomeType;

import java.util.HashMap;
import java.util.Map;

public class HashSetTest {
    public static void main(String args[]){
        String oddsString = "1:1.18;2:2.23;3:5.67";

    }

    private Map<OutcomeType, Double> oddsMap(String oddsString) {
        Map<OutcomeType,Double> oddsMap = new HashMap<>();
        String[] keyValueStrings = oddsString.split(";");
        for(String keyValueString: keyValueStrings){
            String[] keyValue = keyValueString.split(":");
            OutcomeType outcomeType = outcomeTypeService.findByDescription(keyValue[0]);
            if(outcomeType !=null){
                oddsMap.put(outcomeType, Double.valueOf(keyValue[1]));
            }
        }
        return oddsMap;
    }
}
