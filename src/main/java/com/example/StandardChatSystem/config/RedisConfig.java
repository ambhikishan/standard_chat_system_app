package com.example.StandardChatSystem.config;

import com.example.StandardChatSystem.service.RedisReceiver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    // 1. Create a "Listener Container" (The background process that watches Redis)
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                           @Qualifier("chat") MessageListenerAdapter listenerAdapter,
                                            @Qualifier("groupChat") MessageListenerAdapter groupChat) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // Subscribe to the topic "chat"
        container.addMessageListener(listenerAdapter, new PatternTopic("chat:*"));
        container.addMessageListener(groupChat, new PatternTopic("groupchat:*"));
        return container;
    }

//     2. Link the Listener to our specific Java method
    @Bean("chat")
    MessageListenerAdapter listenerAdapter(RedisReceiver receiver) {
        // When a message arrives, call the method "receiveMessage" in RedisReceiver class
        return new MessageListenerAdapter(receiver, "receivePersonalMessage");
    }
    @Bean("groupChat")
    MessageListenerAdapter listenerAdapter2(RedisReceiver receiver)
    {
        return new MessageListenerAdapter(receiver,"groupMessaging");
    }



    // 3. Template to Write data to Redis
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}