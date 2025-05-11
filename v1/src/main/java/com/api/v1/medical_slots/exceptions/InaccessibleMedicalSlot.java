package com.api.v1.medical_slots.exceptions;

public class InaccessibleMedicalSlot extends RuntimeException {
    public InaccessibleMedicalSlot(String licenseNumber) {
        super("Doctor whose license number is %s not the doctor of the sought medical slot.".formatted(licenseNumber));
    }
}
