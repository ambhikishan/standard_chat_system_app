package com.example.StandardChatSystem.controller;

import com.example.StandardChatSystem.pojo.MessageBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 1. User sends message to "/app/sendMessage"
//    @MessageMapping("/sendMessage")
//    public void sendMessage(String message) {
//
//        // 2. Instead of broadcasting directly, we publish to REDIS
//        // This ensures users on OTHER servers also get the message.
//        System.out.println("Publishing to Redis: " + message);
//        redisTemplate.convertAndSend("chat", message);
//    }

    @MessageMapping("/sendPersonalMessage")
    public void sendPersonalMessage(MessageBody message) {
        System.out.println("Publishing to Redis: " + message.toString());
        redisTemplate.convertAndSend("chat:"+message.getTo(), message.toString());
    }
}