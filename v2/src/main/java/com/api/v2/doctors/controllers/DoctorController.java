package com.api.v2.doctors.controllers;

import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.events.DoctorHiringEventPublisher;
import com.api.v2.doctors.events.DoctorModificationEventPublisher;
import com.api.v2.doctors.events.DoctorRehiringEventPublisher;
import com.api.v2.doctors.events.DoctorTerminationEventPublisher;
import com.api.v2.people.dtos.PersonModificationDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/doctors")
public class DoctorController {

    private final DoctorHiringEventPublisher hiringEventPublisher;
    private final DoctorModificationEventPublisher modificationEventPublisher;
    private final DoctorTerminationEventPublisher terminationEventPublisher;
    private final DoctorRehiringEventPublisher rehiringEventPublisher;

    public DoctorController(
            DoctorHiringEventPublisher hiringEventPublisher,
            DoctorModificationEventPublisher modificationEventPublisher,
            DoctorTerminationEventPublisher terminationEventPublisher,
            DoctorRehiringEventPublisher rehiringEventPublisher
    ) {
        this.hiringEventPublisher = hiringEventPublisher;
        this.modificationEventPublisher = modificationEventPublisher;
        this.terminationEventPublisher = terminationEventPublisher;
        this.rehiringEventPublisher = rehiringEventPublisher;
    }

    @PostMapping
    public Mono<DoctorResponseDto> hire(@Valid @RequestBody DoctorHiringDto hiringDto) {
        return hiringEventPublisher.publish(hiringDto);
    }

    @PutMapping("{medicalLicenseNumber}")
    public Mono<Void> modify(
            @PathVariable String medicalLicenseNumber,
            @Valid @RequestBody PersonModificationDto modificationDto
    ) {
        return modificationEventPublisher.publish(medicalLicenseNumber, modificationDto);
    }

    @PatchMapping("{medicalLicenseNumber}/termination")
    public Mono<Void> terminate(@PathVariable String medicalLicenseNumber) {
        return terminationEventPublisher.publish(medicalLicenseNumber);
    }

    @PatchMapping("{medicalLicenseNumber}/rehiring")
    public Mono<Void> rehire(@PathVariable String medicalLicenseNumber) {
        return rehiringEventPublisher.publish(medicalLicenseNumber);
    }
}
