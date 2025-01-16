package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;

import java.time.LocalDateTime;
import java.time.ZoneId;

public record MedicalAppointmentResponseDto(
        String id,
        CustomerResponseDto customerResponseDto,
        DoctorResponseDto doctorResponseDto,
        String type,
        LocalDateTime bookedAt,
        ZoneId bookAtZone,
        LocalDateTime canceledAt,
        ZoneId canceledAtZone,
        LocalDateTime completedAt,
        ZoneId completedAtZone
) {
}
