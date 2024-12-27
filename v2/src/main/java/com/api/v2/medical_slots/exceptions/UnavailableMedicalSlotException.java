package com.api.v2.medical_slots.exceptions;

import java.time.LocalDateTime;

public class UnavailableMedicalSlotException extends RuntimeException {
    public UnavailableMedicalSlotException(LocalDateTime availableAt) {
        super("The datetime %s is already registered for another medical slot.".formatted(availableAt));
    }
}
