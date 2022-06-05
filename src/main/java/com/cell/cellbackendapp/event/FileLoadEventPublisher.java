package com.cell.cellbackendapp.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class FileLoadEventPublisher {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(String message) {

        Map<String, String> dataMap = new LinkedHashMap<>();
        dataMap.put("message",message);
        applicationEventPublisher.publishEvent(new FileLoadEvent<LogData>(LogData.builder().data(dataMap).build()));

    }

}
