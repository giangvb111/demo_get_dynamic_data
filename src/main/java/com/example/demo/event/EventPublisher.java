package com.example.demo.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public <T> void publishCustomEvent(EventType eventType, String message, Object additionalData) {
        Event<T> customEvent = (Event<T>) new Event<>(this, eventType, message, additionalData);
        applicationEventPublisher.publishEvent(customEvent);
    }

    public <T> CompletableFuture<Map<Long,String>> publishEvent(EventType eventType, String message, T data) {
        CompletableFuture<Map<Long,String>> future = new CompletableFuture<>();
        Event<T> event = new Event<>(this, eventType, message, data, future);
        applicationEventPublisher.publishEvent(event);
        return future;
    }


}
