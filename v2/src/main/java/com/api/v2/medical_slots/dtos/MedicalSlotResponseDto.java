package com.api.v2.medical_slots.dtos;

import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

public class MedicalSlotResponseDto {

    private String id;
    private DoctorResponseDto doctor;
    private String availableAt;

    public MedicalSlotResponseDto() {
    }

    protected MedicalSlotResponseDto(MedicalSlot medicalSlot) {
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.id = medicalSlot.getId().toString();
        this.doctor = DoctorResponseMapper.mapToDto(medicalSlot.getDoctor());
        this.availableAt = "%s%s[%s]".formatted(
                medicalSlot.getAvailableAt(),
                medicalSlot.getAvailableAtZoneOffset(),
                medicalSlot.getAvailableAtZoneId()
        );
    }

    public static MedicalSlotResponseDto from(MedicalSlot medicalSlot) {
        return new MedicalSlotResponseDto(medicalSlot);
    }

    public String getId() {
        return id;
    }

    public DoctorResponseDto getDoctor() {
        return doctor;
    }

    public String getAvailableAt() {
        return availableAt;
    }

}
