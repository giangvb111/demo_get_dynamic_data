package com.example.demo.event;

import com.example.demo.master.service.ProductServiceImpl;
import com.example.demo.master.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventListener implements ApplicationListener<Event<?>> {

    private final ProductServiceImpl productService;
    private final WarehouseService warehouseService;
    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);
    @Override
    public void onApplicationEvent(Event<?> event) {
        switch (event.getEventType()) {
            case TYPE_PRODUCT -> productService.handleEvent(event);
            case TYPE_WAREHOUSE -> warehouseService.handleEventWarehouse(event);
            default -> logger.error("Unhandled event type: {}", event.getEventType());
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
