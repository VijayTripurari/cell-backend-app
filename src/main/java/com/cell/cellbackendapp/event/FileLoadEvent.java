package com.cell.cellbackendapp.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FileLoadEvent<T> extends ApplicationEvent {

    private T data;

    public FileLoadEvent(T source) {
        super(source);
        data = source;
    }
}
