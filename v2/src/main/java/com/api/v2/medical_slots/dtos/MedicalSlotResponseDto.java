package com.api.v2.medical_slots.dtos;

import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MedicalSlotResponseDto {

    private String id;
    private DoctorResponseDto doctor;
    private LocalDateTime availableAt;
    private ZoneId availableAtZoneId;

    public MedicalSlotResponseDto() {
    }

    protected MedicalSlotResponseDto(MedicalSlot medicalSlot) {
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.id = medicalSlot.getId().toString();
        this.doctor = DoctorResponseMapper.mapToDto(medicalSlot.getDoctor());
        this.availableAt = medicalSlot.getAvailableAt();
        this.availableAtZoneId = medicalSlot.getAvailableAtZoneId();
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

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }

    public ZoneId getAvailableAtZoneId() {
        return availableAtZoneId;
    }
}
