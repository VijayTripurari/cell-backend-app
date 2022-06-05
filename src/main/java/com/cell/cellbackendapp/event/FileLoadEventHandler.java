package com.cell.cellbackendapp.event;

import com.cell.cellbackendapp.util.JobScheduler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FileLoadEventHandler {

    @Autowired
    JobScheduler jobScheduler;

    @SneakyThrows
    @EventListener
    @Async
    public void publishEvent(FileLoadEvent<LogData> eventData) {
        System.out.println(new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(eventData.getData()));
        jobScheduler.readDataFromFiles();
    }

}
