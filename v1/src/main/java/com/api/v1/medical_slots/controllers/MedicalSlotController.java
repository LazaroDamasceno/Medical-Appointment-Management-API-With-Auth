package com.api.v1.medical_slots.controllers;

import com.api.v1.medical_slots.response.MedicalSlotResponseDto;
import com.api.v1.medical_slots.services.MedicalSlotRegistrationService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/medical-slots")
@RequiredArgsConstructor
public class MedicalSlotController {

    private final MedicalSlotRegistrationService registrationService;

    @PostMapping("{doctorId}/{availableAt}")
    public Mono<ResponseEntity<MedicalSlotResponseDto>> register(@PathVariable String doctorId,
                                                                 @NotNull @PathVariable LocalDateTime availableAt
    ) {
        return registrationService.register(doctorId, availableAt);
    }
}
