package com.api.v2.medical_slots.controllers;

import com.api.v2.common.Id;
import com.api.v2.common.MLN;
import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCancellationService;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRegistrationService;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRetrievalService;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
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

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> register(@RequestBody @Valid MedicalSlotRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("medical-license-number/{medicalLicenseNumber}/id/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> cancel(@PathVariable @MLN String medicalLicenseNumber, @PathVariable @Id String id) {
        return cancellationService.cancel(medicalLicenseNumber, id);
    }

    @GetMapping("/by-id/{id}")
    public Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> findById(@PathVariable @Id String id) {
        return retrievalService.findById(id);
    }

    @GetMapping("/active/by-doctor/{medicalLicenseNumber}")
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllActiveByDoctor(@PathVariable @MLN String medicalLicenseNumber) {
        return retrievalService.findAllCompletedByDoctor(medicalLicenseNumber);
    }

    @GetMapping("/canceled/by-doctor/{medicalLicenseNumber}")
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllCanceledByDoctor(@PathVariable @MLN String medicalLicenseNumber) {
        return retrievalService.findAllCanceledByDoctor(medicalLicenseNumber);
    }

    @GetMapping("/completed/by-doctor/{medicalLicenseNumber}")
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllCompletedByDoctor(@PathVariable @MLN String medicalLicenseNumber) {
        return retrievalService.findAllCompletedByDoctor(medicalLicenseNumber);
    }

    @GetMapping("/by-doctor/{medicalLicenseNumber}")
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllByDoctor(@PathVariable @MLN String medicalLicenseNumber) {
        return retrievalService.findAllActiveByDoctor(medicalLicenseNumber);
    }

    @GetMapping
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAll() {
        return retrievalService.findAll();
    }
}
