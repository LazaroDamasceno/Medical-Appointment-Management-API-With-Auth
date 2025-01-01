package com.api.v2.doctors.controllers;

import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.services.*;
import com.api.v2.people.dtos.PersonModificationDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/doctors")
public class DoctorController {

    private final DoctorHiringService hiringService;
    private final DoctorModificationService modificationService;
    private final DoctorTerminationService terminationService;
    private final DoctorRehiringService rehiringService;
    private final DoctorRetrievalService retrievalService;

    public DoctorController(
            DoctorHiringService hiringService,
            DoctorModificationService modificationService,
            DoctorTerminationService terminationService,
            DoctorRehiringService rehiringService,
            DoctorRetrievalService retrievalService
    ) {
        this.hiringService = hiringService;
        this.modificationService = modificationService;
        this.terminationService = terminationService;
        this.rehiringService = rehiringService;
        this.retrievalService = retrievalService;
    }

    @PostMapping
    public Mono<DoctorResponseDto> hire(@Valid @RequestBody DoctorHiringDto hiringDto) {
        return hiringService.hire(hiringDto);
    }

    @PutMapping("{medicalLicenseNumber}")
    public Mono<Void> modify(
            @PathVariable String medicalLicenseNumber,
            @Valid @RequestBody PersonModificationDto modificationDto
    ) {
        return modificationService.modify(medicalLicenseNumber, modificationDto);
    }

    @PatchMapping("{medicalLicenseNumber}/termination")
    public Mono<Void> terminate(@PathVariable String medicalLicenseNumber) {
        return terminationService.terminate(medicalLicenseNumber);
    }

    @PatchMapping("{medicalLicenseNumber}/rehiring")
    public Mono<Void> rehire(@PathVariable String medicalLicenseNumber) {
        return rehiringService.rehire(medicalLicenseNumber);
    }

    @GetMapping("{medicalLicenseNumber}")
    public Mono<DoctorResponseDto> findByMedicalLicenseNumber(String medicalLicenseNumber) {
        return retrievalService.findByMedicalLicenseNumber(medicalLicenseNumber);
    }

    @GetMapping
    public Flux<DoctorResponseDto> findAll() {
        return retrievalService.findAll();
    }
}
