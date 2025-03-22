package com.api.v2.payments.services;

import com.api.v2.payments.dtos.PaymentResponseDto;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentPaymentService {
    Mono<PaymentResponseDto> payPrivateInsurance(String medicalAppointmentId, String cardId, double price);
    Mono<PaymentResponseDto> payPaidByPatient(String medicalAppointmentId, String cardId, double price);
}
