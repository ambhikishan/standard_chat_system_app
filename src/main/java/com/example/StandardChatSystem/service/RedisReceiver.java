package com.example.StandardChatSystem.service;

import com.example.StandardChatSystem.pojo.MessageBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RedisReceiver {

    @Autowired
    private SimpMessagingTemplate webSocket;



    public void receivePersonalMessage(String message) {
//        System.out.println("Publishing to Redis: " + message);
        System.out.println("Method called sender: ");
        Pattern p = Pattern.compile("to='(.*?)'");
        Pattern messagePattern = Pattern.compile("message='(.*?)'");

        Matcher m = p.matcher(message);
        Matcher m2 = messagePattern.matcher(message);

        if (m.find() && m2.find()) {
            String to = m.group(1);
            String messageBody = m2.group(1);   // only the text inside quotes
            webSocket.convertAndSend("/topic/" + to, messageBody);
        }

//        webSocket.convertAndSendToUser(
//                message.getTo(),
//                "/queue/messages",
//                message
//        );
    }
}
