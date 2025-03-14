package com.api.v2.medical_slots.exceptions;


public class CanceledMedicalSlotException extends RuntimeException {
    public CanceledMedicalSlotException(String id) {
        super("Medical slot whose id is %s is already canceled.".formatted(id));
    }
}
