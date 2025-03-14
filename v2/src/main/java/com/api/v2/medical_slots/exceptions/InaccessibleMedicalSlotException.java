package com.api.v2.medical_slots.exceptions;

import com.api.v2.common.MLN;

public class InaccessibleMedicalSlotException extends RuntimeException {
    public InaccessibleMedicalSlotException(@MLN String medicalLicenseNumber, String slotId) {
        super("Doctor whose medical license number is %s cannot access a medical slot whose id is %s".formatted(medicalLicenseNumber, slotId));
    }
}
