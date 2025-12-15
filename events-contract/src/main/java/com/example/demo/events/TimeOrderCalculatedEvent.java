package com.example.demo.events;

import java.io.Serializable;

public record TimeOrderCalculatedEvent(Integer orderId, Integer score) implements Serializable {}