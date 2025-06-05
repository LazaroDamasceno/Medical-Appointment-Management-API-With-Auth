package com.api.v1.medical_slots;

import com.api.v1.doctors.Doctor;

public interface MedicalSlotFinder {
    MedicalSlot findById(String id);
    MedicalSlot findByIdAndDoctor(String id, Doctor doctor);
}
