package com.elitbet.service.impl;

import com.elitbet.model.EventType;
import com.elitbet.model.Statistic;
import com.elitbet.repository.StatisticRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.IStatistic;
import com.elitbet.service.StatisticService;
import com.elitbet.util.StatisticServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticServiceImpl extends FindById<Statistic,StatisticRepository> implements StatisticService {
    @Autowired
    ApplicationContext context;

    @Override
    public Statistic create(EventType eventType, String parameters) {
        String eventTypeDescription = eventType.getDescription();
        Class serviceClass = StatisticServiceManager.getInstance().getServiceName(eventTypeDescription);
        IStatistic serviceBean = (IStatistic) context.getBean(serviceClass);
        return serviceBean.create(parameters);
    }

    @Override
    public Statistic update(EventType eventType, Statistic statistic, String parameters) {
        String eventTypeDescription = eventType.getDescription();
        Class serviceClass = StatisticServiceManager.getInstance().getServiceName(eventTypeDescription);
        IStatistic serviceBean = (IStatistic) context.getBean(serviceClass);
        return serviceBean.update(statistic,parameters);
    }

    static Map<String,String> parameterMap(String parametersString){
        Map<String, String> parameterMap = new HashMap<>();
        String[] keyValueStrings = parametersString.split(";");
        System.out.println(Arrays.toString(keyValueStrings));
        for(String keyValueString: keyValueStrings){
            String[] keyValue = keyValueString.split(":");
            parameterMap.put(keyValue[0],keyValue[1]);
        }
        return parameterMap;
    }
}