package com.api.v1.nurses.controllers;

import com.api.v1.common.EmptyResponse;
import com.api.v1.nurses.responses.NurseResponseDto;
import com.api.v1.nurses.resquests.NurseRegistrationDto;
import com.api.v1.nurses.services.NurseManagementService;
import com.api.v1.nurses.services.NurseRegistrationService;
import com.api.v1.nurses.services.NurseRetrievalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/nurses")
public class NurseController {

    private final NurseRetrievalService retrievalService;
    private final NurseManagementService managementService;
    private final NurseRegistrationService registrationService;

    public NurseController(NurseRetrievalService retrievalService,
                           NurseManagementService managementService,
                           NurseRegistrationService registrationService
    ) {
        this.retrievalService = retrievalService;
        this.managementService = managementService;
        this.registrationService = registrationService;
    }

    @PostMapping
    public Mono<ResponseEntity<NurseResponseDto>> register(@Valid @RequestBody NurseRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{nurseRegistrationNumber}/termination")
    public Mono<ResponseEntity<EmptyResponse>> terminate(@PathVariable String nurseRegistrationNumber) {
        return managementService.terminate(nurseRegistrationNumber);
    }

    @PatchMapping("{nurseRegistrationNumber}/rehiring")
    public Mono<ResponseEntity<EmptyResponse>> rehire(@PathVariable String nurseRegistrationNumber) {
        return managementService.rehire(nurseRegistrationNumber);
    }

    @GetMapping("{licenseNumber}")
    public Mono<ResponseEntity<NurseResponseDto>> findByLicenseNumber(@PathVariable String licenseNumber) {
        return retrievalService.findByLicenseNumber(licenseNumber);
    }

    @GetMapping
    public ResponseEntity<Flux<NurseResponseDto>> findAll() {
        return retrievalService.findAll();
    }
}
