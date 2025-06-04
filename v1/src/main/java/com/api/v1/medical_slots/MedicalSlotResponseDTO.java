package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public sealed class MedicalSlotResponseDTO
        extends RepresentationModel<MedicalSlotResponseDTO>
        permits ActiveMedicalSlotResponseDTO, CancelledMedicalSlotResponseDTO, CompletedMedicalSlotResponseDTO
{

    private DoctorResponseDTO doctor;
    private LocalDateTime availableAt;

    protected MedicalSlotResponseDTO() {}

    protected MedicalSlotResponseDTO(DoctorResponseDTO doctor, LocalDateTime availableAt) {
        this.doctor = doctor;
        this.availableAt = availableAt;
    }

    public DoctorResponseDTO getDoctor() {
        return doctor;
    }

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }
}
