package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public sealed class DefaultMedicalSlotResponseDTO
        extends RepresentationModel<DefaultMedicalSlotResponseDTO>
        permits CancelledDefaultMedicalSlotResponseDTO,
            CompletedDefaultMedicalSlotResponseDTO,
            MedicalSlotWithAppointmentResponseDTO
{

    private String id;
    private MedicalSlotStatus status;
    private DoctorResponseDTO doctor;
    private LocalDateTime availableAt;
    private LocalDateTime createdAt;

    protected DefaultMedicalSlotResponseDTO() {}

    protected DefaultMedicalSlotResponseDTO(String id,
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
