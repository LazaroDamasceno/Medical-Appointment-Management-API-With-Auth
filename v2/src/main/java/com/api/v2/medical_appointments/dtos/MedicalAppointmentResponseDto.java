package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;

import java.time.LocalDateTime;

public record MedicalAppointmentResponseDto(
        CustomerResponseDto customerResponseDto,
        DoctorResponseDto doctorResponseDto,
        LocalDateTime bookedAt,
        LocalDateTime canceledAt,
        LocalDateTime completedAt
) {
}
