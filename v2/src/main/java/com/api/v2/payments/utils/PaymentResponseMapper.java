package com.api.v2.payments.utils;

import com.api.v2.cards.utils.CardResponseMapper;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.payments.domain.Payment;
import com.api.v2.payments.dtos.PaymentResponseDto;
import reactor.core.publisher.Mono;

public final class PaymentResponseMapper {

    public static PaymentResponseDto mapToDto(Payment payment) {
        return new PaymentResponseDto(
                payment.id(),
                CardResponseMapper.mapToDto(payment.card()),
                MedicalAppointmentResponseMapper.mapToDto(payment.medicalAppointment()),
                "%s%s[%s]".formatted(payment.paidAt(), payment.paidAtZoneOffset(), payment.paidAtZoneId()),
                payment.price()
        );
    }

    public static Mono<PaymentResponseDto> mapToMono(Payment payment) {
        return Mono.just(mapToDto(payment));
    }
}
