package com.example.statistics_service.listeners;

import com.example.demo.events.OrderCreatedEvent;
import com.example.demo.events.OrderDeletedEvent;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderEventListener {
    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);
    private static final String EXCHANGE_NAME = "order-exchange";
    private static final String QUEUE_NAME = "notification-queue";
    private Map<Integer, String> orders = new HashMap<>();
    private final Set<Integer> processedBookCreations = ConcurrentHashMap.newKeySet();

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = QUEUE_NAME,
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = EXCHANGE_NAME, type = "topic", durable = "true"),
                    key = "order.created"
            )
    )
    public void handleOrderCreatedEvent(@Payload OrderCreatedEvent event, Channel channel,
                                       @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            log.info("Received OrderCreatedEvent: {}", event);

            if (!processedBookCreations.add(event.id())) {
                log.warn("Duplicate event received for orderId: {}", event.id());
                channel.basicAck(deliveryTag, false); return;
            }

            log.info("Add to map order '{}'!", event.promo());
            orders.put(event.id(), event.promo());
            log.info("Count map = " + orders.size());
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            log.error("Failed to process event: {}. Sending to DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
