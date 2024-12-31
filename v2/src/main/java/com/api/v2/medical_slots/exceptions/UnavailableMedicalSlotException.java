package com.api.v2.medical_slots.exceptions;

import java.time.LocalDateTime;

public class UnavailableMedicalSlotException extends RuntimeException {

    public UnavailableMedicalSlotException(LocalDateTime availableAt) {
        super("There's no medical slot whose available datetime is %s".formatted(availableAt));
    }
}
