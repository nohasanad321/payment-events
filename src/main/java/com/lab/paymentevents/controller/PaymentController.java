package com.lab.paymentevents.controller;

import com.lab.paymentevents.data.PaymentCreateReq;
import com.lab.paymentevents.data.PaymentCreatedEvent;
import com.lab.paymentevents.service.PaymentEventProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/v1/payments")
@RestController
public class PaymentController {

    private final PaymentEventProducer paymentEventProducer;

    public PaymentController(PaymentEventProducer paymentEventProducer) {
        this.paymentEventProducer = paymentEventProducer;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPayment(
             @RequestBody PaymentCreateReq request) {

        UUID paymentId = UUID.randomUUID();

        PaymentCreatedEvent event = new PaymentCreatedEvent(
                paymentId,
                request.getAmount(),
                request.getCurrency(),
                Instant.now()
        );

        paymentEventProducer.publish(event);
        Map<String, String> response = new HashMap<>();
        response.put("paymentId", paymentId.toString());
        response.put("status", "success");
        response.put("Amount", request.getAmount().toString() + " " + request.getCurrency());
        return ResponseEntity.accepted()
                .body(response);
    }
}
