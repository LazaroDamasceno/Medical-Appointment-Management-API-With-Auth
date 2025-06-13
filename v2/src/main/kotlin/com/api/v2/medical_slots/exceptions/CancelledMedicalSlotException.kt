package com.api.v2.medical_slots.exceptions

class CancelledMedicalSlotException(id: String)
    : RuntimeException("Medical slot whose id is $id is cancelled.")