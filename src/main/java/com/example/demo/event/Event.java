package com.example.demo.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Event<T> extends ApplicationEvent {

    private EventType eventType;
    private T data;
    private String message;
    private CompletableFuture<Map<Long,String>> future;

    public Event(Object source, EventType eventType, String message, T data) {
        super(source);
        this.eventType = eventType;
        this.message = message;
        this.data = data;
    }

    public Event(EventPublisher source, EventType eventType, String message, T data, CompletableFuture<Map<Long,String>> future) {
        super(source);
        this.eventType = eventType;
        this.message = message;
        this.data = data;
        this.future= future;
    }
}

