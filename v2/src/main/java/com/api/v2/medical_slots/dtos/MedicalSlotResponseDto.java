package com.api.v2.medical_slots.dtos;

import com.api.v2.doctors.dtos.DoctorResponseDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public record MedicalSlotResponseDto(
        DoctorResponseDto doctorResponseDto,
        LocalDateTime availableAt,
        ZoneId availableAtZone
) {
}
