package com.api.v2.doctors.controllers;

import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.services.*;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/doctors")
public class DoctorController {

    private final DoctorHiringService hiringService;
    private final DoctorTerminationService terminationService;
    private final DoctorRehiringService rehiringService;
    private final DoctorRetrievalService retrievalService;

    public DoctorController(
            DoctorHiringService hiringService,
            DoctorTerminationService terminationService,
            DoctorRehiringService rehiringService,
            DoctorRetrievalService retrievalService
    ) {
        this.hiringService = hiringService;
        this.terminationService = terminationService;
        this.rehiringService = rehiringService;
        this.retrievalService = retrievalService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<HalResourceWrapper<DoctorResponseDto, Void>> hire(@Valid @RequestBody DoctorHiringDto hiringDto) {
        return hiringService.hire(hiringDto);
    }

    @PatchMapping("{medicalLicenseNumber}/termination")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> terminate(@PathVariable String medicalLicenseNumber) {
        return terminationService.terminate(medicalLicenseNumber);
    }

    @PatchMapping("{medicalLicenseNumber}/rehiring")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> rehire(@PathVariable String medicalLicenseNumber) {
        return rehiringService.rehire(medicalLicenseNumber);
    }

    @GetMapping("{medicalLicenseNumber}")
    public Mono<HalResourceWrapper<DoctorResponseDto, Void>> findByMedicalLicenseNumber(@PathVariable String medicalLicenseNumber) {
        return retrievalService.findByMedicalLicenseNumber(medicalLicenseNumber);
    }

    @GetMapping
    public Flux<DoctorResponseDto> findAll() {
        return retrievalService.findAll();
    }
}
