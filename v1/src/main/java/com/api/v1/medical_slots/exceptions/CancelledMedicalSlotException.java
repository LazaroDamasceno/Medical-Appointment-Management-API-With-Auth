package com.api.v1.medical_slots.exceptions;

public class CancelledMedicalSlotException extends RuntimeException {
    public CancelledMedicalSlotException(String id) {
        super("Medical slot whose id is %s is currently cancelled.".formatted(id));
    }
}
