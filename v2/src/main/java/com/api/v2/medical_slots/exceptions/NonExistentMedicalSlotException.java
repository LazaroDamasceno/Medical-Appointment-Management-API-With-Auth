package com.api.v2.medical_slots.exceptions;

public class NonExistentMedicalSlotException extends RuntimeException {
    public NonExistentMedicalSlotException(Object id) {
        super("Medical slot whose id is %s was not found.".formatted(id));
    }
}
