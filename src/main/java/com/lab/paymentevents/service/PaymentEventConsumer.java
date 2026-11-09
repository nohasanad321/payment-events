package com.lab.paymentevents.service;

import com.lab.paymentevents.data.PaymentCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.apache.logging.log4j.*;


@Component
public class PaymentEventConsumer {
    private static final Logger log =
            LogManager.getLogger("PaymentEventConsumer");

    @KafkaListener(
            topics = "${app.kafka.payment-topic}",
            groupId = "${app.kafka.payment-group}"
    )
    public void consume(PaymentCreatedEvent event) {
        System.out.println("Payment received: id= " + event.paymentId() +
                ", amount= " + event.amount() +
                ", currency= " + event.currency());
        log.info(
                "Payment received: id= {}, amount= {}, currency={}",
                event.paymentId(),
                event.amount(),
                event.currency()
        );

    }
}
