package com.api.v1.medical_slots.controllers;

import com.api.v1.common.LicenseNumber;
import com.api.v1.common.ObjectId;
import com.api.v1.medical_slots.MedicalSlotResponseDTO;
import com.api.v1.medical_slots.services.MedicalSlotRegistrationService;
import com.api.v1.medical_slots.services.MedicalSlotRetrievalService;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/medical-slots")
public class MedicalSlotController {

    private final MedicalSlotRetrievalService retrievalService;
    private final MedicalSlotRegistrationService registrationService;

    public MedicalSlotController(
            MedicalSlotRetrievalService retrievalService,
            MedicalSlotRegistrationService registrationService
    ) {
        this.retrievalService = retrievalService;
        this.registrationService = registrationService;
    }

    @GetMapping("{id}/{medicalLicenseNumber}")
    public ResponseEntity<MedicalSlotResponseDTO> findByIdAndDoctor(@PathVariable @ObjectId String id,
                                                                    @PathVariable @LicenseNumber String medicalLicenseNumber
    ) {
        return retrievalService.findByIdAndDoctor(id, medicalLicenseNumber);
    }

    @GetMapping("{medicalLicenseNumber}")
    public ResponseEntity<Page<MedicalSlotResponseDTO>> findAllByDoctor(@PathVariable @LicenseNumber String medicalLicenseNumber,
                                                                        @RequestParam Pageable pageable
    ) {
        return retrievalService.findAllByDoctor(medicalLicenseNumber, pageable);
    }

    @GetMapping
    public ResponseEntity<Page<MedicalSlotResponseDTO>> findAll(@RequestParam Pageable pageable) {
        return retrievalService.findAll(pageable);
    }

    @PostMapping("{medicalLicenseNumber}/{availableAt}")
    public ResponseEntity<MedicalSlotResponseDTO> register(
            @PathVariable @LicenseNumber String medicalLicenseNumber,
            @PathVariable @NotNull LocalDateTime availableAt
    ) {
        return registrationService.register(medicalLicenseNumber, availableAt);
    }
}
