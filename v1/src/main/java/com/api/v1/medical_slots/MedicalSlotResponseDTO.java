package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDto;

import java.time.LocalDateTime;

public final class MedicalSlotResponseDTO {

    private DoctorResponseDto doctor;
    private LocalDateTime availableAt;

    private MedicalSlotResponseDTO() {}

    private MedicalSlotResponseDTO(DoctorResponseDto doctor, LocalDateTime availableAt) {
        this.doctor = doctor;
        this.availableAt = availableAt;
    }

    public static MedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new MedicalSlotResponseDTO(medicalSlot.getDoctor().toDto(), medicalSlot.getAvailableAt());
    }

    public DoctorResponseDto getDoctor() {
        return doctor;
    }

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }
}
