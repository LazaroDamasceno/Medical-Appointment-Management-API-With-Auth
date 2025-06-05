package com.api.v1.medical_slots;

import com.api.v1.doctors.Doctor;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "MedicalSlots")
public class MedicalSlot {

    @Id
    private String id;
    private Doctor doctor;
    private MedicalSlotStatus status;
    private LocalDateTime availableAt;
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime completedAt;

    private MedicalSlot() {}

    private MedicalSlot(Doctor doctor, LocalDateTime availableAt) {
        this.id = UUID.randomUUID().toString();
        this.status = MedicalSlotStatus.ACTIVE;
        this.doctor = doctor;
        this.availableAt = availableAt;
        this.createdAt = LocalDateTime.now();
    }

    public static MedicalSlot of(Doctor doctor, LocalDateTime availableAt) {
        return new MedicalSlot(doctor, availableAt);
    }

    public void markAsCancelled() {
        this.status = MedicalSlotStatus.CANCELLED;
        this.cancelledAt = LocalDateTime.now();
    }

    public void markAsCompleted() {
        this.status = MedicalSlotStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public MedicalSlotResponseDTO toDTO() {
        if (cancelledAt != null && completedAt == null) {
            CancelledMedicalSlotResponseDTO.from(this);
        }
        else if (cancelledAt == null && completedAt != null) {
            CompletedMedicalSlotResponseDTO.from(this);
        }
        return MedicalSlotResponseDTO.from(this);
    }

    public String getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public MedicalSlotStatus getStatus() {
        return status;
    }

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
