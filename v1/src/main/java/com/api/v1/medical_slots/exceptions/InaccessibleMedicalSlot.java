package com.api.v1.medical_slots.exceptions;

public class InaccessibleMedicalSlot extends RuntimeException {
    public InaccessibleMedicalSlot() {
        super("Provided doctor is not the doctor of the sought medical slot.");
    }
}
