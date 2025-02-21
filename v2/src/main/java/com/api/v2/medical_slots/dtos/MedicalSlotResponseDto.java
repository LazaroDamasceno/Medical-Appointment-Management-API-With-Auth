package com.api.v2.medical_slots.dtos;

import com.api.v2.common.Id;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;

public record MedicalSlotResponseDto(
        @Id String id,
        DoctorResponseDto doctor,
        MedicalAppointmentResponseDto medicalAppointment,
        String availableAt,
        String canceledAt,
        String completedAt
) {
}
