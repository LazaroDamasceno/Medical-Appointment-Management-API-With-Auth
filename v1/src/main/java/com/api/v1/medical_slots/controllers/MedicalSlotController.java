package com.api.v1.medical_slots.controllers;

import com.api.v1.common.EmptyResponse;
import com.api.v1.medical_slots.response.MedicalSlotResponseDto;
import com.api.v1.medical_slots.services.MedicalSlotManagementService;
import com.api.v1.medical_slots.services.MedicalSlotRegistrationService;
import com.api.v1.medical_slots.services.MedicalSlotRetrievalService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/medical-slots")
public class MedicalSlotController {

    private final MedicalSlotManagementService managementService;
    private final MedicalSlotRegistrationService registrationService;
    private final MedicalSlotRetrievalService retrievalService;

    public MedicalSlotController(MedicalSlotManagementService managementService,
                                 MedicalSlotRegistrationService registrationService,
                                 MedicalSlotRetrievalService retrievalService
    ) {
        this.managementService = managementService;
        this.registrationService = registrationService;
        this.retrievalService = retrievalService;
    }

    @PostMapping("{doctorLicenseNumber}/{availableAt}")
    @Operation(summary = "Register a new medical slot")
    public Mono<ResponseEntity<MedicalSlotResponseDto>> register(@PathVariable String doctorLicenseNumber,
                                                                 @NotNull @PathVariable LocalDateTime availableAt
    ) {
        return registrationService.register(doctorLicenseNumber, availableAt);
    }

    @PatchMapping("{doctorLicenseNumber}/{slotId}/cancellation")
    @Operation(summary = "Cancel a medical slot")
    public Mono<ResponseEntity<EmptyResponse>> cancel(@PathVariable String doctorLicenseNumber, @PathVariable String slotId) {
        return managementService.cancel(doctorLicenseNumber, slotId);
    }

    @PatchMapping("{doctorLicenseNumber}/{slotId}/completion")
    @Operation(summary = "Complete a medical slot")
    public Mono<ResponseEntity<EmptyResponse>> complete(@PathVariable String doctorLicenseNumber, @PathVariable String slotId) {
        return managementService.complete(doctorLicenseNumber, slotId);
    }

    @GetMapping("{doctorLicenseNumber}/{slotId}")
    @Operation(summary = "Find a medical slot by doctor and id")
    public Mono<ResponseEntity<MedicalSlotResponseDto>> findByDoctorAndId(@PathVariable String doctorLicenseNumber, @PathVariable String slotId) {
        return retrievalService.findByDoctorAndId(doctorLicenseNumber, slotId);
    }

    @GetMapping("{doctorLicenseNumber}")
    @Operation(summary = "Find medical slots by doctor")
    public ResponseEntity<Flux<MedicalSlotResponseDto>> findAllByDoctor(@PathVariable String doctorLicenseNumber) {
        return retrievalService.findAllByDoctor(doctorLicenseNumber);
    }

    @GetMapping
    @Operation(summary = "Find all medical slots")
    public ResponseEntity<Flux<MedicalSlotResponseDto>> findAll() {
        return retrievalService.findAll();
    }
}
