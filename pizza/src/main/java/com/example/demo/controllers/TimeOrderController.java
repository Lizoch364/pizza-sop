package com.example.demo.controllers;

import com.example.analytics.AnalyticsServiceGrpc;
import com.example.analytics.TimeOrderRequest;
import com.example.demo.config.RabbitMQConfig;

import com.example.demo.events.TimeOrderCalculatedEvent;
import com.example.demo.services.OrderService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pizza_api.pizza_api.dto.OrderResponse;

@RestController
public class TimeOrderController {
    @GrpcClient("analytics-service")
    private AnalyticsServiceGrpc.AnalyticsServiceBlockingStub analyticsStub;
    private OrderService orderService;

    private final RabbitTemplate rabbitTemplate;

    public TimeOrderController(RabbitTemplate rabbitTemplate, OrderService orderService) {
        this.rabbitTemplate = rabbitTemplate;
        this.orderService = orderService;
    }

    @PostMapping("/api/order/{id}/time")
    public String rateTime(@PathVariable int id) {
        // Вызов gRPC
        try {
            OrderResponse order = orderService.findById(id);
            var request = TimeOrderRequest.newBuilder().setOrderId(id).setPromo(order.getPromo()).build();
            var gRpcResponse = analyticsStub.calculateTimeOrder(request);

            // Отправка события в Fanout
            var event = new TimeOrderCalculatedEvent((int) gRpcResponse.getOrderId(), gRpcResponse.getTimeScore());

            // Для Fanout routingKey не важен, оставляем пустым ""
            rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", event);

            return "Time calculated: " + gRpcResponse.getTimeScore();
        } catch (Exception e) {
            throw new RuntimeException("Error time calculated: -1");
        }
    }
}
