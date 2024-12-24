package com.api.v2.medical_slots.utils;

import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;

public class MedicalSlotResponseMapper {

    public static MedicalSlotResponseDto map(MedicalSlot medicalSlot) {
        return new MedicalSlotResponseDto(
                DoctorResponseMapper.mapToDto(medicalSlot.getDoctor()),
                medicalSlot.getAvailableAt()
        );
    }
}
