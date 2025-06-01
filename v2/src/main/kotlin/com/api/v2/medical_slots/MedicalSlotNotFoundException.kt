package com.api.v2.medical_slots;

class MedicalSlotNotFoundException(id: String)
    : RuntimeException("Medical slot whose id is $id was not found.")
