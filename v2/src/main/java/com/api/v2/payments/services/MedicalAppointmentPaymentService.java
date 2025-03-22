package com.api.v2.payments.services;

import com.api.v2.payments.dtos.PaymentResponseDto;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentPaymentService {
    Mono<PaymentResponseDto> pay(String medicalAppointmentId, String cardId, double price);
}
