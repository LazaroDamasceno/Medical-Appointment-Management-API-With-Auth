package com.api.v2.medical_slots.exceptions;

import com.api.v2.doctors.exceptions.ImmutableDoctorException;
import org.bson.types.ObjectId;

public class CanceledMedicalSlotException extends ImmutableDoctorException {
    public CanceledMedicalSlotException(ObjectId id) {
        super("Medical slot whose id is %s is already canceled.".formatted(id));
    }
}
