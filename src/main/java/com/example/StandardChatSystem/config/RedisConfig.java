package com.example.StandardChatSystem.config;

import com.example.StandardChatSystem.service.RedisReceiver;
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
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // Subscribe to the topic "chat"
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        return container;
    }

    // 2. Link the Listener to our specific Java method
    @Bean
    MessageListenerAdapter listenerAdapter(RedisReceiver receiver) {
        // When a message arrives, call the method "receiveMessage" in RedisReceiver class
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    // 3. Template to Write data to Redis
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}