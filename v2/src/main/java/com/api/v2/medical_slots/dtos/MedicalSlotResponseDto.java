package com.api.v2.medical_slots.dtos;

import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MedicalSlotResponseDto {

    private String id;
    private DoctorResponseDto doctorResponseDto;
    private LocalDateTime availableAt;
    private ZoneId availableAtZone;

    public MedicalSlotResponseDto() {
    }

    protected MedicalSlotResponseDto(MedicalSlot medicalSlot) {
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.id = medicalSlot.getId().toString();
        this.doctorResponseDto = DoctorResponseMapper.mapToDto(medicalSlot.getDoctor());
        this.availableAt = medicalSlot.getAvailableAt();
        this.availableAtZone = medicalSlot.getAvailableAtZone();
    }

    public static MedicalSlotResponseDto create(MedicalSlot medicalSlot) {
        return new MedicalSlotResponseDto(medicalSlot);
    }

    public String getId() {
        return id;
    }

    public DoctorResponseDto getDoctorResponseDto() {
        return doctorResponseDto;
    }

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }

    public ZoneId getAvailableAtZone() {
        return availableAtZone;
    }
}
