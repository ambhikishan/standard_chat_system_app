package com.example.StandardChatSystem.controller;

import com.example.StandardChatSystem.pojo.MessageBody;
import com.example.StandardChatSystem.pojo.TypingNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Controller
public class ChatController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @MessageMapping("/sendPersonalMessage")
    public void sendPersonalMessage(MessageBody message) throws ExecutionException, InterruptedException {

            System.out.println("Publishing to Redis: " + message.toString());
            redisTemplate.convertAndSend("chat:" + message.getFrom(), message.toString()); // "chat:message.getFrom() is the connected user to redis server through the websocket"
            // this channel in the redis server will get deleted when the user is offline and free the cpu resource
        if(!message.toString().contains("#usertyping")) {
            String conversationKey = message.getFrom() + "@" + message.getTo();
            kafkaTemplate.send("chat-db-topic", conversationKey, message.toString()).get();
        }
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