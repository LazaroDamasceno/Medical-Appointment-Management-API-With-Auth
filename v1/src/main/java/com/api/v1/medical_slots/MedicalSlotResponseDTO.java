package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public sealed class MedicalSlotResponseDTO
        extends RepresentationModel<MedicalSlotResponseDTO>
        permits CancelledMedicalSlotResponseDTO, CompletedMedicalSlotResponseDTO
{

    private String id;
    private MedicalSlotStatus status;
    private DoctorResponseDTO doctor;
    private LocalDateTime availableAt;
    private LocalDateTime createdAt;

    protected MedicalSlotResponseDTO() {}

    protected MedicalSlotResponseDTO(String id,
                                     MedicalSlotStatus status,
                                     DoctorResponseDTO doctor,
                                     LocalDateTime availableAt,
                                     LocalDateTime createdAt
    ) {
        this.id = id;
        this.status = status;
        this.doctor = doctor;
        this.availableAt = availableAt;
        this.createdAt = createdAt;
    }

    public static MedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new MedicalSlotResponseDTO(
                medicalSlot.getId(),
                MedicalSlotStatus.ACTIVE,
                medicalSlot.getDoctor().toDTO(),
                medicalSlot.getAvailableAt(),
                medicalSlot.getCreatedAt()
        );
    }

    public DoctorResponseDTO getDoctor() {
        return doctor;
    }

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }

    public MedicalSlotStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
