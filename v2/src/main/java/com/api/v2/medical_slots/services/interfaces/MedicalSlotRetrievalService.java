package com.api.v2.medical_slots.services.interfaces;

import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalSlotRetrievalService {
    Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> findById(String id);
    Flux<MedicalSlotResponseDto> findAllByDoctor(String medicalLicenseNumber);
    Flux<MedicalSlotResponseDto> findAll();
}
