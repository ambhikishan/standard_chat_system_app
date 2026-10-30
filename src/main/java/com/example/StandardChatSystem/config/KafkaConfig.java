package com.example.StandardChatSystem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    NewTopic chatDbTopic()
    {
        return TopicBuilder.name("chat-db-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        // Ensure this matches your Kafka server address
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String,String> kafkaTemplate()
    {
        return new KafkaTemplate<String,String>(producerFactory());
    }
}

//I think if I don't use the configuration the Kafka will automatically generate the topic for me,
//
//You are correct. By default, Kafka brokers have a setting called auto.create.topics.enable set to true, meaning the broker will automatically create the topic the first time your Spring Boot application sends a message to it.
//
//You can safely skip the KafkaConfig class and rely on this auto-creation, especially for local development.
//
//However, there is a specific reason it is standard practice to define the topic explicitly in code: partition control.
//
//If Kafka auto-creates the topic, it uses the broker's default settings, which is usually just 1 partition. This means all chat messages go into a single queue, and only one consumer thread can read from it at a time. By explicitly defining the topic with .partitions(3), you split it into three parallel queues. This allows multiple instances of your application to process database saves concurrently as your chat traffic grows.
//
//For your current testing, you can delete the configuration bean and let Kafka handle the topic creation automatically.