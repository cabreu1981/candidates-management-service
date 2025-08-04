package com.cabreu.candidatesmanagementservice.adapter.kafka;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public AuthEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAuthEvent(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
