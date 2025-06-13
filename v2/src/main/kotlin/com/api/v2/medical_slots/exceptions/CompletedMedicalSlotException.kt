package com.api.v2.medical_slots.exceptions

class CompletedMedicalSlotException(id: String)
    : RuntimeException("Medical slot whose id is $id is completed.")