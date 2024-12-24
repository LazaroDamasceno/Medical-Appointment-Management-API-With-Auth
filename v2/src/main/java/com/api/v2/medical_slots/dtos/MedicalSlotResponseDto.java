package com.api.v2.medical_slots.dtos;

import com.api.v2.doctors.dtos.DoctorResponseDto;

import java.time.LocalDateTime;

public record MedicalSlotResponseDto(
        DoctorResponseDto doctorResponseDto,
        LocalDateTime availableAt
) {
}
