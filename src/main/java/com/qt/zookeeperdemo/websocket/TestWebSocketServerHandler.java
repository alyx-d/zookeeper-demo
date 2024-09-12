package com.qt.zookeeperdemo.websocket;

import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class TestWebSocketServerHandler extends TextWebSocketHandler {


    private Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("session id {}", session.getId());
        sessionMap.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getId()).close();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("receive message {}", message.getPayload());
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void sendMessage() {
        sessionMap.values().forEach(session -> {
            try {
                session.sendMessage(new TextMessage("hello"));
                System.out.println("send success");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
