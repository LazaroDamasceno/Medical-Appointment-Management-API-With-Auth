package com.api.v1.medical_slots;

import com.api.v1.medical_slots.enums.MedicalSlotStatus;

public final class MedicalSlotResponseWrapperImpl {

    public static DefaultMedicalSlotResponseDTO toDTO(MedicalSlot medicalSlot) {
        if (medicalSlot.status().equals(MedicalSlotStatus.CANCELLED)) {
            return new CancelledDefaultMedicalSlotResponseDTO(
                    medicalSlot.id(),
                    medicalSlot.status(),
                    medicalSlot.toDTO().getDoctor(),
                    medicalSlot.availableAt(),
                    medicalSlot.createdAt(),
                    medicalSlot.cancelledAt()
                );
        }
        else if (medicalSlot.status().equals(MedicalSlotStatus.COMPLETED)) {
                return new CompletedDefaultMedicalSlotResponseDTO(
                        medicalSlot.id(),
                        medicalSlot.status(),
                        medicalSlot.toDTO().getDoctor(),
                        medicalSlot.availableAt(),
                        medicalSlot.createdAt(),
                        medicalSlot.completedAt(),
                        medicalSlot.medicalAppointment().toDTO()
                );
            }
        else if (medicalSlot.status().equals(MedicalSlotStatus.ACTIVE)
                    && medicalSlot.medicalAppointment() != null)
        {
            return new MedicalSlotWithAppointmentResponseDTO(
                    medicalSlot.id(),
                    medicalSlot.status(),
                    medicalSlot.toDTO().getDoctor(),
                    medicalSlot.availableAt(),
                    medicalSlot.createdAt(),
                    medicalSlot.medicalAppointment().toDTO()
            );
        }
        return new DefaultMedicalSlotResponseDTO(
                medicalSlot.id(),
                medicalSlot.status(),
                medicalSlot.toDTO().getDoctor(),
                medicalSlot.availableAt(),
                medicalSlot.createdAt()
            );
        }
}
