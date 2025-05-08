package com.api.v1.medical_slots.exceptions;

public class NotActiveMedicalSlotException extends RuntimeException {

    public NotActiveMedicalSlotException(String id) {
      super("Medical slot whose id is %s is currently not active.".formatted(id));
    }

    public NotActiveMedicalSlotException() {
        super("Sought medical slot is not active.");
    }
}
