package com.api.v2.medical_slots

class InaccessibleMedicalSlotException(id: String, doctorLicenseNumber: String)
    : RuntimeException(
    "Medical slot whose id is $id is not accessible by the doctor whose license number is $doctorLicenseNumber"
    )