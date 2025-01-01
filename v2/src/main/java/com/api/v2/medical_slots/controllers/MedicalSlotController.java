package com.api.v2.medical_slots.controllers;

import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCancellationService;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRegistrationService;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRetrievalService;
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

    @PostMapping
    public Mono<MedicalSlotResponseDto> register(@RequestBody MedicalSlotRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{id}")
    public Mono<Void> cancel(@PathVariable String id) {
        return cancellationService.cancel(id);
    }

    @GetMapping("{id}")
    public Mono<MedicalSlotResponseDto> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping("{medicalLicenseNumber}")
    public Flux<MedicalSlotResponseDto> findAllByDoctor(@PathVariable String medicalLicenseNumber) {
        return retrievalService.findAllByDoctor(medicalLicenseNumber);
    }

    @GetMapping
    public Flux<MedicalSlotResponseDto> findAll() {
        return retrievalService.findAll();
    }
}
