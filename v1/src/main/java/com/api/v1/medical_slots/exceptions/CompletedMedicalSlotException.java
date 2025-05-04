package com.api.v1.medical_slots.exceptions;

public class CompletedMedicalSlotException extends RuntimeException {
    public CompletedMedicalSlotException(String id) {
        super("Medical slot whose id is %s is currently completed.".formatted(id));
    }
}
