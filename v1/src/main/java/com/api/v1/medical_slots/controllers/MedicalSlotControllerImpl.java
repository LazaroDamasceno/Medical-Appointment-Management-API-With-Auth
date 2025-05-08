package com.api.v1.medical_slots.controllers;

import com.api.v1.common.EmptyResponse;
import com.api.v1.medical_slots.controllers.exposed.MedicalSlotController;
import com.api.v1.medical_slots.response.MedicalSlotResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/medical-slots")
public class MedicalSlotControllerImpl extends MedicalSlotController {

    @PostMapping("{doctorId}/{availableAt}")
    @Operation(summary = "Register a new medical slot")
    public Mono<ResponseEntity<MedicalSlotResponseDto>> register(@PathVariable String doctorId,
                                                                 @NotNull @PathVariable LocalDateTime availableAt
    ) {
        return registrationService.register(doctorId, availableAt);
    }

    @PatchMapping("{doctorId}/{slotId}/cancellation")
    @Operation(summary = "Cancel a medical slot")
    public Mono<ResponseEntity<EmptyResponse>> cancel(@PathVariable String doctorId, @PathVariable String slotId) {
        return managementService.cancel(doctorId, slotId);
    }

    @PatchMapping("{doctorId}/{slotId}/completion")
    @Operation(summary = "Complete a medical slot")
    public Mono<ResponseEntity<EmptyResponse>> complete(@PathVariable String doctorId, @PathVariable String slotId) {
        return managementService.complete(doctorId, slotId);
    }

    @GetMapping("{doctorId}/{slotId}")
    @Operation(summary = "Find a medical slot by doctor and id")
    public Mono<ResponseEntity<MedicalSlotResponseDto>> findByDoctorAndId(@PathVariable String doctorId, @PathVariable String slotId) {
        return retrievalService.findByDoctorAndId(doctorId, slotId);
    }

    @GetMapping("{doctorId}")
    @Operation(summary = "Find medical slots by doctor")
    public ResponseEntity<Flux<MedicalSlotResponseDto>> findAllByDoctor(@PathVariable String doctorId) {
        return retrievalService.findAllByDoctor(doctorId);
    }

    @GetMapping
    @Operation(summary = "Find all medical slots")
    public ResponseEntity<Flux<MedicalSlotResponseDto>> findAll() {
        return retrievalService.findAll();
    }
}
