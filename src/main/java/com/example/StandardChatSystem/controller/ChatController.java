package com.example.StandardChatSystem.controller;

import com.example.StandardChatSystem.pojo.MessageBody;
import com.example.StandardChatSystem.pojo.TypingNotification;
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
        redisTemplate.convertAndSend("chat:"+message.getFrom(), message.toString()); // "chat:message.getFrom() is the connected user to redis server through the websocket"
                                           // this channel in the redis server will get deleted when the user is offline and free the cpu resource
    }

    @MessageMapping("/chat/typing")
    public void typing(TypingNotification typingNotification){
        redisTemplate.convertAndSend("chat:"+typingNotification.getFrom(),typingNotification.toString());
    }

    @MessageMapping("sendMessageInGroup")
    public void sendMessageInGroup(MessageBody message){
        System.out.println(message.toString());
        redisTemplate.convertAndSend("groupchat:",message.getMessage());
    }
}