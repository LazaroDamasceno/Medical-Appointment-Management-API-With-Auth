package com.api.v2.payments.dtos;

import com.api.v2.cards.dtos.CardResponseDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;

public record PaymentResponseDto(
        String id,
        CardResponseDto card,
        MedicalAppointmentResponseDto medicalAppointment,
        String paidAt
) {
}
