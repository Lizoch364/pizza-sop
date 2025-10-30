package com.example.demo.events;

import java.io.Serializable;
import java.util.List;

public record OrderCreatedEvent(
        int id,
        List<Integer> dishesId,
        String promo,
        String status
) implements Serializable {}
