package com.example.StandardChatSystem.config;

import com.example.StandardChatSystem.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer "))
                throw new IllegalArgumentException("Missing JWT");

            String token = authHeader.substring(7);

            String username = jwtService.validateAndExtract(token);

            accessor.setUser(new UsernamePasswordAuthenticationToken(username, null));
        }
        if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String destination = accessor.getDestination();
            String username = accessor.getUser().getName();
            // Allow only: /topic/{username}
            if (!destination.equals("/topic/" + username)) {
                throw new IllegalArgumentException("Unauthorized subscription");
            }
        }

        return message;
    }
}
