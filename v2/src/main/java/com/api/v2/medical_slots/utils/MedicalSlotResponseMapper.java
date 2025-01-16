package com.api.v2.medical_slots.utils;

import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import reactor.core.publisher.Mono;

public class MedicalSlotResponseMapper {

    public static MedicalSlotResponseDto mapToDto(MedicalSlot medicalSlot) {
        return MedicalSlotResponseDto.create(medicalSlot);
    }

    public static Mono<MedicalSlotResponseDto> mapToMono(MedicalSlot medicalSlot) {
        return Mono.just(mapToDto(medicalSlot));
    }
}
