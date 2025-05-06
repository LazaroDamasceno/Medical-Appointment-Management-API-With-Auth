package com.api.v1.medical_slots.controllers;

import com.api.v1.common.EmptyResponse;
import com.api.v1.medical_slots.response.MedicalSlotResponseDto;
import com.api.v1.medical_slots.services.MedicalSlotManagementService;
import com.api.v1.medical_slots.services.MedicalSlotRegistrationService;
import com.api.v1.medical_slots.services.MedicalSlotRetrievalService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/medical-slots")
@RequiredArgsConstructor
public class MedicalSlotController {

    private final MedicalSlotRegistrationService registrationService;
    private final MedicalSlotManagementService managementService;
    private final MedicalSlotRetrievalService retrievalService;

    @PostMapping("{doctorId}/{availableAt}")
    public Mono<ResponseEntity<MedicalSlotResponseDto>> register(@PathVariable String doctorId,
                                                                 @NotNull @PathVariable LocalDateTime availableAt
    ) {
        return registrationService.register(doctorId, availableAt);
    }

    @PatchMapping("{doctorId}/{medicalSlotId}/cancellation")
    public Mono<ResponseEntity<EmptyResponse>> cancel(@PathVariable String doctorId, @PathVariable String medicalSlotId) {
        return managementService.cancel(doctorId, medicalSlotId);
    }

    @PatchMapping("{doctorId}/{medicalSlotId}/completion")
    public Mono<ResponseEntity<EmptyResponse>> complete(@PathVariable String doctorId, @PathVariable String medicalSlotId) {
        return managementService.complete(doctorId, medicalSlotId);
    }

    @GetMapping("{doctorId}/{medicalSlotId}")
    public Mono<ResponseEntity<MedicalSlotResponseDto>> findByDoctorAndId(@PathVariable String doctorId, @PathVariable String medicalSlotId) {
        return retrievalService.findByDoctorAndId(doctorId, medicalSlotId);
    }

    @GetMapping("{doctorId}")
    public ResponseEntity<Flux<MedicalSlotResponseDto>> findAllByDoctor(@PathVariable String doctorId) {
        return retrievalService.findAllByDoctor(doctorId);
    }

    @GetMapping
    public ResponseEntity<Flux<MedicalSlotResponseDto>> findAll() {
        return retrievalService.findAll();
    }
}
