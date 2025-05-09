package com.api.v1.medical_slots.response;

import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class MedicalSlotResponseDto extends RepresentationModel<MedicalSlotResponseDto> {

    private String id;
    private DoctorResponseDto doctor;
    private LocalDateTime availableAt;
    private MedicalSlotStatus status;

    MedicalSlotResponseDto() {}

    protected MedicalSlotResponseDto(String id,
                                   DoctorResponseDto doctor,
                                   LocalDateTime availableAt,
                                   MedicalSlotStatus status
    ) {
        this.id = id;
        this.doctor = doctor;
        this.availableAt = availableAt;
        this.status = status;
    }

    public static MedicalSlotResponseDto from(MedicalSlot medicalSlot) {
        return new MedicalSlotResponseDto(
                medicalSlot.getId(),
                medicalSlot.getDoctor().toDto(),
                medicalSlot.getAvailableAt(),
                medicalSlot.getStatus()
        );
    }
}
