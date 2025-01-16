package com.api.v2.medical_slots.dtos;

import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;

public record MedicalSlotResponseDto(
        ObjectId id,
        DoctorResponseDto doctorResponseDto,
        LocalDateTime availableAt,
        ZoneId availableAtZone,
        MedicalAppointmentResponseDto medicalAppointmentResponseDto
) {
}
