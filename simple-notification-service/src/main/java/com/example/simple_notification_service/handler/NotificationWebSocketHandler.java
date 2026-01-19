package com.example.simple_notification_service.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(NotificationWebSocketHandler.class);
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String query = session.getUri().getQuery();

        log.info("userId = " + getUserId(query));
        sessions.put(getUserId(query), session);

        log.info("Новое подключение: id={}, всего активных: {}",
                session.getId(), sessions.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        log.debug("Сообщение от {}: {}", session.getId(), payload);

        if (payload.equals("PING")) {
            sendMessage(session, new TextMessage("PONG"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        log.info("Отключение: id={}, причина={}, осталось: {}",
                session.getId(), status.getReason(), sessions.size());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Ошибка транспорта для сессии {}: {}",
                session.getId(), exception.getMessage());
        sessions.remove(session);
    }

    public int broadcast(String message, String userId) {
        TextMessage textMessage = new TextMessage(message);
        int sent = 0;

//        for (WebSocketSession session : sessions.values()) {
//            if (sendMessage(session, textMessage)) {
//                sent++;
//            }
//        }

        if (sendMessage(sessions.get(userId), textMessage)) {
            sent++;
        }

        log.info("Broadcast: отправлено {}/{} клиентам", sent, sessions.size());
        return sent;
    }

    private boolean sendMessage(WebSocketSession session, TextMessage message) {
        if (!session.isOpen()) {
            sessions.remove(session);
            return false;
        }
        try {
            session.sendMessage(message);
            return true;
        } catch (IOException e) {
            log.warn("Ошибка отправки в сессию {}: {}", session.getId(), e.getMessage());
            sessions.remove(session);
            return false;
        }
    }

    private String getUserId(String query) {
        return query.split("=")[1];
    }

    public int getActiveConnections() {
        return sessions.size();
    }
}
