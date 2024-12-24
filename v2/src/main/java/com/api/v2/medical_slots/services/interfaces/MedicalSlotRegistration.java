package com.api.v2.medical_slots.services.interfaces;

import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import reactor.core.publisher.Mono;

public interface MedicalSlotRegistration {
    Mono<MedicalSlotResponseDto> register(MedicalSlotRegistrationDto registrationDto);
}
