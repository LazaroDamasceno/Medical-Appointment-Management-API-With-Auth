package com.api.v1.medical_slots.exceptions;

public class MedicalSlotNotFoundException extends RuntimeException {
    public MedicalSlotNotFoundException(String id) {
        super("Medical slot whose id is %s was not found.");
    }
}
