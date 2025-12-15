package com.example.audit_service.listeners;

import com.example.demo.events.TimeOrderCalculatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TimeOrderListener {
    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    @RabbitListener(
            bindings = @QueueBinding(
                    // Уникальное имя очереди для уведомлений!
                    value = @Queue(name = "q.audit.analytics", durable = "true"),
                    exchange = @Exchange(name = "analytics-fanout", type = "fanout")
            )
    )
    public void handleTiming(TimeOrderCalculatedEvent event) {
        log.info("NOTIFICATION: Order status change. Order {} has a new execution time: {}", event.orderId(), event.score());
    }
}
