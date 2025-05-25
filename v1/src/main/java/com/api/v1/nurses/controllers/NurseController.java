package com.api.v1.nurses.controllers;

import com.api.v1.common.LicenseNumber;
import com.api.v1.nurses.requests.NurseRegistrationDTO;
import com.api.v1.nurses.responses.NurseResponseDTO;
import com.api.v1.nurses.services.NurseRegistrationService;
import com.api.v1.nurses.services.NurseRetrievalService;
import com.api.v1.nurses.services.NurseUpdateService;
import com.api.v1.people.requests.PersonUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/nurses")
public class NurseController {

    private final NurseRetrievalService retrievalService;
    private final NurseRegistrationService registrationService;
    private final NurseUpdateService updateService;

    public NurseController(NurseRetrievalService retrievalService,
                           NurseRegistrationService registrationService,
                           NurseUpdateService updateService
    ) {
        this.retrievalService = retrievalService;
        this.registrationService = registrationService;
        this.updateService = updateService;
    }

    @GetMapping("{licenseNumber}")
    public ResponseEntity<NurseResponseDTO> findByLicenseNumber(@PathVariable @LicenseNumber String licenseNumber) {
        return retrievalService.findByLicenseNumber(licenseNumber);
    }

    @GetMapping
    public ResponseEntity<Page<NurseResponseDTO>> findAll(@RequestParam Pageable pageable) {
        return retrievalService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<NurseResponseDTO> register(@RequestBody @Valid NurseRegistrationDTO registrationDTO) {
        return registrationService.register(registrationDTO);
    }

    @PatchMapping("{licenseNumber}")
    public ResponseEntity<Void> update(@PathVariable @LicenseNumber String licenseNumber,
                                       @RequestBody @Valid PersonUpdateDTO updateDTO
    ) {
        return updateService.update(licenseNumber, updateDTO);
    }
}
