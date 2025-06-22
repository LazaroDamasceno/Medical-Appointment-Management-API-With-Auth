package com.api.v1.medical_slots;

public interface MedicalSlotConversionStrategy {
    DefaultMedicalSlotResponseDTO converter(MedicalSlot medicalSlot);
}
