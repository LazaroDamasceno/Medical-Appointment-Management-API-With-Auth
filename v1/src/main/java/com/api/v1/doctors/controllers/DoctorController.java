package com.api.v1.doctors.controllers;

import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.requests.DoctorRegistrationDTO;
import com.api.v1.doctors.response.DoctorResponseDto;
import com.api.v1.doctors.services.DoctorManagementService;
import com.api.v1.doctors.services.DoctorRegistrationService;
import com.api.v1.doctors.services.DoctorRetrievalService;
import com.api.v1.people.requests.PersonUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorRegistrationService registrationService;
    private final DoctorRetrievalService retrievalService;
    private final DoctorManagementService managementService;

    public DoctorController(DoctorRegistrationService registrationService,
                            DoctorRetrievalService retrievalService,
                            DoctorManagementService managementService
    ) {
        this.registrationService = registrationService;
        this.retrievalService = retrievalService;
        this.managementService = managementService;
    }

    @PatchMapping("/{licenseNumber}/termination")
    public ResponseEntity<Void> terminate(@PathVariable @LicenseNumber String licenseNumber) {
        return managementService.terminate(licenseNumber);
    }

    @PatchMapping("/{licenseNumber}/rehiring")
    public ResponseEntity<Void> rehire(@PathVariable @LicenseNumber String licenseNumber) {
        return managementService.rehire(licenseNumber);
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDto> register(@RequestBody @Valid DoctorRegistrationDTO registrationDto) {
        return registrationService.register(registrationDto);
    }

    @GetMapping("/{licenseNumber}")
    public ResponseEntity<DoctorResponseDto> findByLicenseNumber(@PathVariable @LicenseNumber String licenseNumber) {
        return retrievalService.findByLicenseNumber(licenseNumber);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorResponseDto>> findAll(@RequestBody Pageable pageable) {
        return retrievalService.findAll(pageable);
    }

    @PatchMapping("/{licenseNumber}/updating")
    public ResponseEntity<Void> update(@PathVariable @LicenseNumber String licenseNumber,
                                       @Valid @RequestBody PersonUpdateDTO personUpdateDto
    ) {
        return managementService.update(licenseNumber, personUpdateDto);
    }
}
