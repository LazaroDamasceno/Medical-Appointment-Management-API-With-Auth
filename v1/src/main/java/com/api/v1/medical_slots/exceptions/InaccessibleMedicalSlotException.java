package com.api.v1.medical_slots.exceptions;

public class InaccessibleMedicalSlotException extends RuntimeException {
    public InaccessibleMedicalSlotException(String id, String medicalLicenseNumber) {
        super("Medical slot whose id is %s cannot be accessed by doctor whose license number is %s.".formatted(id, medicalLicenseNumber));
    }
}
