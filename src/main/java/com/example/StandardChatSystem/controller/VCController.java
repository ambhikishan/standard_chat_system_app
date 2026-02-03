package com.example.StandardChatSystem.controller;

import com.example.StandardChatSystem.pojo.Rtc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class VCController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @MessageMapping("/video/call")
    public void videoCall(Rtc rtc)
    {
        redisTemplate.convertAndSend("chat:*"+rtc.getFrom(), rtc.toString());
        System.out.println("video call initiated");
    }
}

