package com.api.v1.medical_slots.services;

import com.api.v1.medical_slots.response.MedicalSlotResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalSlotRetrievalService {
    Mono<ResponseEntity<MedicalSlotResponseDto>> findByDoctorAndId(String doctorLicenseNumber, String slotId);
    ResponseEntity<Flux<MedicalSlotResponseDto>> findAllByDoctor(String doctorLicenseNumber);
    ResponseEntity<Flux<MedicalSlotResponseDto>> findAll();
}
