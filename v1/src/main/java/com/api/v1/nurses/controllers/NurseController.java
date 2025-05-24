package com.api.v1.nurses.controllers;

import com.api.v1.common.LicenseNumber;
import com.api.v1.nurses.responses.NurseResponseDTO;
import com.api.v1.nurses.services.NurseRetrievalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/nurses")
public class NurseController {

    private final NurseRetrievalService retrievalService;

    public NurseController(NurseRetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    @GetMapping("{licenseNumber}")
    public ResponseEntity<NurseResponseDTO> findByLicenseNumber(@PathVariable @LicenseNumber String licenseNumber) {
        return retrievalService.findByLicenseNumber(licenseNumber);
    }

    @GetMapping
    public ResponseEntity<Page<NurseResponseDTO>> findAll(@RequestParam Pageable pageable) {
        return retrievalService.findAll(pageable);
    }
}
