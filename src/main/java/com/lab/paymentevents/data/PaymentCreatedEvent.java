package com.lab.paymentevents.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentCreatedEvent(UUID paymentId,
                                  BigDecimal amount,
                                  String currency,
                                  Instant createdAt) {
}
