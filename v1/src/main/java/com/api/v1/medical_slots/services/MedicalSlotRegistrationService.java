package com.api.v1.medical_slots.services;

import com.api.v1.medical_slots.response.MedicalSlotResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalSlotRegistrationService {
    Mono<ResponseEntity<MedicalSlotResponseDto>> register(String doctorId, LocalDateTime availableAt);
}
