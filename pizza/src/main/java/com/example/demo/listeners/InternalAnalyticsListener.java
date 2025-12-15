package com.example.demo.listeners;

import com.example.demo.events.TimeOrderCalculatedEvent;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InternalAnalyticsListener {

    @RabbitListener(
            bindings = @QueueBinding(
                    // Нужно другое имя очереди!
                    value = @Queue(name = "q.demorest.analytics.log", durable = "true"),
                    exchange = @Exchange(name = "analytics-fanout", type = "fanout")
            )
    )
    public void logRating(TimeOrderCalculatedEvent event) {
        System.out.println("We have just calculated the order preparation time " + event.orderId());
    }
}

