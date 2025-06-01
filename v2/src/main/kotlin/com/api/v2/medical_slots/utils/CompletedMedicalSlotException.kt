package com.api.v2.medical_slots.utils

class CompletedMedicalSlotException(id: String)
    : RuntimeException("Medical slot whose id is $id is currently completed.")