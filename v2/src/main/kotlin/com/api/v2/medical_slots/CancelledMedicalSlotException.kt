package com.api.v2.medical_slots

class CancelledMedicalSlotException(id: String)
    : RuntimeException("Medical slot whose id is $id is currently cancelled.")