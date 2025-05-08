package com.api.v1.medical_slots.exceptions;

public class CanceledMedicalSlotException extends RuntimeException {

    public CanceledMedicalSlotException(String id) {
        super("Medical slot whose id is %s is currently canceled.".formatted(id));
    }

    public CanceledMedicalSlotException() {
        super("Sought medical slot is currently canceled.");
    }
}
