package com.api.v2.medical_slots.controllers;

import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCancellationService;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRegistrationService;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRetrievalService;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/medical-slots")
public class MedicalSlotController {

    private final MedicalSlotRegistrationService registrationService;
    private final MedicalSlotCancellationService cancellationService;
    private final MedicalSlotRetrievalService retrievalService;

    public MedicalSlotController(
            MedicalSlotRegistrationService registrationService,
            MedicalSlotCancellationService cancellationService,
            MedicalSlotRetrievalService retrievalService
    ) {
        this.registrationService = registrationService;
        this.cancellationService = cancellationService;
        this.retrievalService = retrievalService;
    }

    @Operation(summary = "Register a medical slot")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> register(@RequestBody @Valid MedicalSlotRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @Operation(summary = "Cancel a medical slot")
    @PatchMapping("medical-license-number/{medicalLicenseNumber}/id/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> cancel(@PathVariable String medicalLicenseNumber, @PathVariable String id) {
        return cancellationService.cancel(medicalLicenseNumber, id);
    }

    @Operation(summary = "Retrieve a medical slot by its id")
    @GetMapping("/by-id/{id}")
    public Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @Operation(summary = "Retrieve active medical slots by its doctor")
    @GetMapping("/active/by-doctor/{medicalLicenseNumber}")
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllActiveByDoctor(@PathVariable String medicalLicenseNumber) {
        return retrievalService.findAllCompletedByDoctor(medicalLicenseNumber);
    }

    @Operation(summary = "Retrieve canceled medical slots by its doctor")
    @GetMapping("/canceled/by-doctor/{medicalLicenseNumber}")
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllCanceledByDoctor(@PathVariable String medicalLicenseNumber) {
        return retrievalService.findAllCanceledByDoctor(medicalLicenseNumber);
    }

    @Operation(summary = "Retrieve completed medical slots by its doctor")
    @GetMapping("/completed/by-doctor/{medicalLicenseNumber}")
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllCompletedByDoctor(@PathVariable String medicalLicenseNumber) {
        return retrievalService.findAllCompletedByDoctor(medicalLicenseNumber);
    }

    @Operation(summary = "Retrieve medical slots by its doctor")
    @GetMapping("/by-doctor/{medicalLicenseNumber}")
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllByDoctor(@PathVariable String medicalLicenseNumber) {
        return retrievalService.findAllActiveByDoctor(medicalLicenseNumber);
    }

    @Operation(summary = "Retrieve all medical slots")
    @GetMapping
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAll() {
        return retrievalService.findAll();
    }
}
