package com.api.v2.medical_slots.services.interfaces;

import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalSlotRetrievalService {
    Mono<MedicalSlotResponseDto> findById(String id);
    Flux<MedicalSlotResponseDto> findAllByDoctor(String medicalLicenseNumber);
    Flux<MedicalSlotResponseDto> findAll();
}
