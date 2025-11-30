package com.example.StandardChatSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisReceiver {

    @Autowired
    private SimpMessagingTemplate webSocket;

    // This method is triggered by Redis
    public void receiveMessage(String message) {
        System.out.println("Redis Broadcast Received: " + message);

        // Push the message to ALL users connected to THIS server via WebSocket
        // Destination: /topic/public
        webSocket.convertAndSend("/topic/public", message);
    }
}