package com.lab.paymentevents.service;

import com.lab.paymentevents.data.PaymentCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducer {
    private final KafkaTemplate<String, PaymentCreatedEvent> kafka;

    private final String topic;

    public PaymentEventProducer(
            KafkaTemplate<String, PaymentCreatedEvent> kafka,
            @Value("${app.kafka.payment-topic}") String topic) {
        this.kafka = kafka;
        this.topic = topic;
    }

    public void publish(PaymentCreatedEvent event) {
        kafka.send(
                topic,
                event.paymentId().toString(),
                event
        );
    }
}
