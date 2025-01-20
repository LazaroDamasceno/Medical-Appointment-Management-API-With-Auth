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
                return CompletedMedicalSlotResponseDto.from(medicalSlot);
            }
            else if (medicalSlot.getCanceledAt() != null) {
                return CanceledMedicalSlotWithMedicalAppointmentResponseDto.from(medicalSlot);
            }
            return MedicalSlotWithMedicalAppointmentResponseDto.from(medicalSlot);
        }
        else if (optional.isEmpty()) {
            return CanceledMedicalSlotWithoutMedicalAppointmentResponseDto.from(medicalSlot);
        }
        return MedicalSlotResponseDto.from(medicalSlot);
    }

    public static Mono<MedicalSlotResponseDto> mapToMono(MedicalSlot medicalSlot) {
        return Mono.just(mapToDto(medicalSlot));
    }
}
