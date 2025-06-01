package com.api.v2.medical_slots.exceptions

class InaccessibleMedicalSlotException(doctorLicenseNumber: String)
    : RuntimeException(
    "Sought medical slot is not accessible by the doctor whose license number is $doctorLicenseNumber"
    )