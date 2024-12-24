package com.api.v2.medical_slots.exceptions;

import org.bson.types.ObjectId;

public class NonExistentMedicalSlotException extends RuntimeException {
    public NonExistentMedicalSlotException(ObjectId id) {
        super("Medical slot whose id is %s was not found.".formatted(id));
    }
}
