package com.example.StandardChatSystem.service;

import com.example.StandardChatSystem.pojo.MessageBody;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RedisReceiver {

    @Autowired
    private SimpMessagingTemplate webSocket;



    public void receivePersonalMessage(String message) {
//        System.out.println("Publishing to Redis: " + message);

        ObjectMapper response = new ObjectMapper();


        System.out.println("Method called sender: ");
        Pattern p = Pattern.compile("to='(.*?)'");
        Pattern messagePattern = Pattern.compile("message='(.*?)'");
        Pattern fromPattern = Pattern.compile("from='(.*?)'");
        Pattern typePattern = Pattern.compile("type='(.*?)'");
        Pattern sdpPattern = Pattern.compile("sdp='(.*?)'");

        Matcher m = p.matcher(message);
        Matcher m2 = messagePattern.matcher(message);
        Matcher m3 = fromPattern.matcher(message);
        Matcher m4 = typePattern.matcher(message);
        Matcher m5 = sdpPattern.matcher(message);

        if(m4.find() && m5.find() && m3.find() && m.find())
        {
            String type = m4.group(1);
            String sdp = m5.group(1);
            String to = m.group(1);
            String from = m3.group(1);
            webSocket.convertAndSend("/topic/"+to,response.writeValueAsString(Map.of("to", to, "from", from , "type",type,"sdp",sdp)));
            System.out.println(message + "response");
            return;
        }


        if (m.find() && m2.find() && m3.find()) {
            String to = m.group(1);
            String messageBody = m2.group(1);   // only the text inside quotes
            String from = m3.group(1);
            webSocket.convertAndSend("/topic/" + to,Optional.of(Map.of("to",to,"from",from,"message",message.substring(26+to.length()+from.length(),message.length()-2))));

        }

//        webSocket.convertAndSendToUser(
//                message.getTo(),
//                "/queue/messages",
//                message
//        );
    }

    public void groupMessaging(String message)
    {

        webSocket.convertAndSend("/topic/group", Optional.of(Map.of("message", message)));
    }


}
