package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "order-exchange";
    public static final String ROUTING_KEY_ORDER_CREATED = "order.created";

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue myQueue() {
        return new Queue(ROUTING_KEY_ORDER_CREATED);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange myExchange) {
        return BindingBuilder.bind(queue).to(myExchange).with(ROUTING_KEY_ORDER_CREATED);
    }
}


