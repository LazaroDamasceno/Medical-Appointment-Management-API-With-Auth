package com.api.v1.payments.services;

import com.api.v1.payments.domain.Payment;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentPaymentService {
    Mono<Payment> pay(String appointmentId, double price);
}
