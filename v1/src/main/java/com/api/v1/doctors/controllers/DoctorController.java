package com.api.v1.doctors.controllers;

import com.api.v1.common.EmptyResponse;
import com.api.v1.doctors.requests.DoctorRegistrationDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.doctors.services.DoctorManagementService;
import com.api.v1.doctors.services.DoctorRegistrationService;
import com.api.v1.doctors.services.DoctorRetrievalService;
import com.api.v1.doctors.services.DoctorUpdatingService;
import com.api.v1.people.requests.PersonUpdatingDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRegistrationService registrationService;
    private final DoctorUpdatingService updatingService;
    private final DoctorRetrievalService retrievalService;
    private final DoctorManagementService managementService;

    @PostMapping
    @Operation(summary = "Register a new doctor")
    public Mono<ResponseEntity<DoctorResponseDto>> register(@Valid @RequestBody DoctorRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{doctorId}")
    @Operation(summary = "Update a new doctor")
    public Mono<ResponseEntity<EmptyResponse>> update(@PathVariable String doctorId, @Valid @RequestBody PersonUpdatingDto updatingDto) {
        return updatingService.update(doctorId, updatingDto);
    }

    @GetMapping("{id}")
    @Operation(summary = "Find a doctor by id")
    public Mono<DoctorResponseDto> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Find all doctors")
    public Flux<DoctorResponseDto> findAll() {
        return retrievalService.findAll();
    }

    @PatchMapping("{doctorId}/termination")
    @Operation(summary = "Terminate a doctor")
    public Mono<ResponseEntity<EmptyResponse>> terminate(@PathVariable String doctorId) {
        return null;
    }

    @PatchMapping("{doctorId}/rehiring")
    @Operation(summary = "Rehire a doctor")
    public Mono<ResponseEntity<EmptyResponse>> rehire(@PathVariable String doctorId) {
        return null;
    }
}
