package com.api.v1.medical_slots.services;

import com.api.v1.medical_slots.response.MedicalSlotResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalSlotRetrievalService {
    Mono<ResponseEntity<MedicalSlotResponseDto>> findByDoctorAndId(String doctorId, String medicalSlotId);
    Flux<MedicalSlotResponseDto> findAllByDoctor(String doctorId);
    Flux<MedicalSlotResponseDto> findAll();
}
