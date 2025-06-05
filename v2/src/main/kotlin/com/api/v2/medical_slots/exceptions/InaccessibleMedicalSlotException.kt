package com.api.v2.medical_slots.exceptions

class InaccessibleMedicalSlotException(id: String, medicalLicenseNumber: String) : RuntimeException(
    "Medical slot whose id is $id cannot be accessed by doctor whose license number is $medicalLicenseNumber"
)