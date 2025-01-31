package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;

public record MedicalAppointmentResponseDto(
        String id,
        CustomerResponseDto customer,
        DoctorResponseDto doctor,
        String type,
        String bookedAt,
        String canceledAt,
        String completedAt
) {
}
