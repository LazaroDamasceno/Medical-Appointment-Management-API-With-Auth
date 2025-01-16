package com.api.v2.medical_slots.services.interfaces;

import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Mono;

public interface MedicalSlotRegistrationService {
    Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> register(MedicalSlotRegistrationDto registrationDto);
}
