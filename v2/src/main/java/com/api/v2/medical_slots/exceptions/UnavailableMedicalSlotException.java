package com.api.v2.medical_slots.exceptions;

public class UnavailableMedicalSlotException extends RuntimeException {

    public UnavailableMedicalSlotException(String message) {
        super(message);
    }
}
