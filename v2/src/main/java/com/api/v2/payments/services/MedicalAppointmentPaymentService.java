package com.api.v2.payments.services;

import com.api.v2.payments.dtos.PaymentResponseDto;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentPaymentService {
    Mono<PaymentResponseDto> payPrivateInsurance(String customerId, String medicalAppointmentId, String cardId, double price);
    Mono<PaymentResponseDto> payPublicInsurance(String customerId, String medicalAppointmentId, String cardId);
    Mono<PaymentResponseDto> payPaidByPatient(String customerId, String medicalAppointmentId, String cardId, double price);
}
