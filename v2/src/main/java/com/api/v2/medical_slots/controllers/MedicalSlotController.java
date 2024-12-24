package com.api.v2.medical_slots.controllers;

import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCancellationService;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRegistrationService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/medical-slots")
public class MedicalSlotController {

    private final MedicalSlotRegistrationService registrationService;
    private final MedicalSlotCancellationService cancellationService;

    public MedicalSlotController(
            MedicalSlotRegistrationService registrationService,
            MedicalSlotCancellationService cancellationService
    ) {
        this.registrationService = registrationService;
        this.cancellationService = cancellationService;
    }

    @PostMapping
    public Mono<MedicalSlotResponseDto> register(@RequestBody MedicalSlotRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{id}")
    public Mono<Void> cancel(@PathVariable String id) {
        return cancellationService.cancel(id);
    }
}
