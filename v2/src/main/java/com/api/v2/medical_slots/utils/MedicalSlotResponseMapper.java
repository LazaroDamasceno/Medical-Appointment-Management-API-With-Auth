package com.api.v2.medical_slots.utils;

import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import reactor.core.publisher.Mono;

public class MedicalSlotResponseMapper {

    public static MedicalSlotResponseDto mapToDto(MedicalSlot medicalSlot) {
        if (medicalSlot.getMedicalAppointment() == null) {
            return new MedicalSlotResponseDto(
                    medicalSlot.getId(),
                    DoctorResponseMapper.mapToDto(medicalSlot.getDoctor()),
                    medicalSlot.getAvailableAt(),
                    medicalSlot.getAvailableAtZone(),
                    null
            );
        }
        return new MedicalSlotResponseDto(
                medicalSlot.getId(),
                DoctorResponseMapper.mapToDto(medicalSlot.getDoctor()),
                medicalSlot.getAvailableAt(),
                medicalSlot.getAvailableAtZone(),
                MedicalAppointmentResponseMapper.mapToDto(medicalSlot.getMedicalAppointment())
        );
    }

    public static Mono<MedicalSlotResponseDto> mapToMono(MedicalSlot medicalSlot) {
        return Mono.just(mapToDto(medicalSlot));
    }
}
