package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;

import java.time.LocalDateTime;
import java.time.ZoneId;

public record MedicalAppointmentResponseDto(
        CustomerResponseDto customerResponseDto,
        DoctorResponseDto doctorResponseDto,
        LocalDateTime bookedAt,
        ZoneId bookAtZone,
        LocalDateTime canceledAt,
        ZoneId canceledAtZone,
        LocalDateTime completedAt,
        ZoneId completedAtZone
) {
}
