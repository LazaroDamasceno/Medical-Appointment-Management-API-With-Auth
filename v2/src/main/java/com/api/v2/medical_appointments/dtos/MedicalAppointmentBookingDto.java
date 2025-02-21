package com.api.v2.medical_appointments.dtos;

import java.time.LocalDateTime;

import com.api.v2.common.Id;
import com.api.v2.common.MLN;

public record MedicalAppointmentBookingDto(
        @Id String customerId,
        @MLN String medicalLicenseNumber,
        LocalDateTime bookedAt
) {
}
