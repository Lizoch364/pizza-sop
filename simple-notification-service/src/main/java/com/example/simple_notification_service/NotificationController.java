package com.example.simple_notification_service;

import com.example.simple_notification_service.handler.NotificationWebSocketHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationWebSocketHandler handler;

    public NotificationController(NotificationWebSocketHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/broadcast")
    public ResponseEntity<Map<String, Object>> broadcast(@RequestBody BroadcastDto dto) {
        int sent = handler.broadcast(dto.message(), dto.userId());
        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "sentTo", sent,
                "message", dto.message()
        ));
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        return ResponseEntity.ok(Map.of(
                "activeConnections", handler.getActiveConnections()
        ));
    }

}
