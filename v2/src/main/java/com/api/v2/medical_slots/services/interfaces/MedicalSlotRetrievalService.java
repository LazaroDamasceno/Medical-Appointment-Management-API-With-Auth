package com.api.v2.medical_slots.services.interfaces;

import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalSlotRetrievalService {
    Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> findById(String slotId);
    Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllActiveByDoctor(String medicalLicenseNumber);
    Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllCanceledByDoctor(String medicalLicenseNumber);
    Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllCompletedByDoctor(String medicalLicenseNumber);
    Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAll();
}
