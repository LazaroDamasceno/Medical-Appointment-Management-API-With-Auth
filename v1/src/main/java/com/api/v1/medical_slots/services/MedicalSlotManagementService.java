package com.api.v1.medical_slots.services;

import com.api.v1.common.EmptyResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface MedicalSlotManagementService {
    Mono<ResponseEntity<EmptyResponse>> cancel(String doctorLicenseNumber, String slotId);
    Mono<ResponseEntity<EmptyResponse>> complete(String doctorLicenseNumber, String slotId);
}
