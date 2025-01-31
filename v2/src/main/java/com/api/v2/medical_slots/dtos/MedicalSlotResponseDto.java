package com.api.v2.medical_slots.dtos;

import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_slots.domain.MedicalSlot;

public record MedicalSlotResponseDto(
        String id,
        DoctorResponseDto doctor,
        MedicalAppointmentResponseDto medicalAppointment,
        String availableAt,
        String canceledAt,
        String completedAt
) {
}
