package com.api.v1.doctors.controllers;

import com.api.v1.common.EmptyResponse;
import com.api.v1.doctors.controllers.exposed.DoctorController;
import com.api.v1.doctors.requests.DoctorRegistrationDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.doctors.services.DoctorManagementService;
import com.api.v1.doctors.services.DoctorRegistrationService;
import com.api.v1.doctors.services.DoctorRetrievalService;
import com.api.v1.doctors.services.DoctorUpdatingService;
import com.api.v1.people.requests.PersonUpdatingDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/doctors")
public class DoctorController implements DoctorController {

    private final DoctorManagementService managementService;
    private final DoctorRegistrationService registrationService;
    private final DoctorRetrievalService retrievalService;
    private final DoctorUpdatingService updatingService;

    public DoctorController(DoctorManagementService managementService,
                            DoctorRegistrationService registrationService,
                            DoctorRetrievalService retrievalService,
                            DoctorUpdatingService updatingService
    ) {
        this.managementService = managementService;
        this.registrationService = registrationService;
        this.retrievalService = retrievalService;
        this.updatingService = updatingService;
    }

    @PostMapping
    @Operation(summary = "Register a new doctor")
    public Mono<ResponseEntity<DoctorResponseDto>> register(@Valid @RequestBody DoctorRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{doctorLicenseNumber}")
    @Operation(summary = "Update a new doctor")
    public Mono<ResponseEntity<EmptyResponse>> update(@PathVariable String doctorLicenseNumber, @Valid @RequestBody PersonUpdatingDto updatingDto) {
        return updatingService.update(doctorLicenseNumber, updatingDto);
    }

    @GetMapping("{id}")
    @Operation(summary = "Find a doctor by license number")
    public Mono<ResponseEntity<DoctorResponseDto>> findByLicenseNumber(@PathVariable String licenseNumber) {
        return retrievalService.findByLicenseNumber(licenseNumber);
    }

    @GetMapping
    @Operation(summary = "Find all doctors")
    public ResponseEntity<Flux<DoctorResponseDto>> findAll() {
        return retrievalService.findAll();
    }

    @PatchMapping("{doctorLicenseNumber}/termination")
    @Operation(summary = "Terminate a doctor")
    public Mono<ResponseEntity<EmptyResponse>> terminate(@PathVariable String doctorLicenseNumber) {
        return managementService.terminate(doctorLicenseNumber);
    }

    @PatchMapping("{doctorLicenseNumber}/rehiring")
    @Operation(summary = "Rehire a doctor")
    public Mono<ResponseEntity<EmptyResponse>> rehire(@PathVariable String doctorLicenseNumber) {
        return managementService.rehire(doctorLicenseNumber);
    }
}
