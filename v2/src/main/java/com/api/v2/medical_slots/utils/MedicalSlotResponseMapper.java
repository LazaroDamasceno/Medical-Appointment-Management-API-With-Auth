package com.api.v2.medical_slots.utils;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.dtos.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class MedicalSlotResponseMapper {

    public static MedicalSlotResponseDto mapToDto(MedicalSlot medicalSlot) {
        Optional<MedicalAppointment> optional = Optional.ofNullable(medicalSlot.getMedicalAppointment());
        if (optional.isPresent()) {
            if (medicalSlot.getCompletedAt() != null) {
                return CompletedMedicalSlotResponseDto.create(medicalSlot);
            }
            else if (medicalSlot.getCanceledAt() != null) {
                return CanceledMedicalSlotWithMedicalAppointmentResponseDto.create(medicalSlot);
            }
            return MedicalSlotWithMedicalAppointmentResponseDto.create(medicalSlot);
        }
        else if (optional.isEmpty()) {
            return CanceledMedicalSlotWithoutMedicalAppointmentResponseDto.create(medicalSlot);
        }
        return MedicalSlotResponseDto.create(medicalSlot);
    }

    public static Mono<MedicalSlotResponseDto> mapToMono(MedicalSlot medicalSlot) {
        return Mono.just(mapToDto(medicalSlot));
    }
}
