package com.api.v2.doctors.events;

import com.api.v2.doctors.services.DoctorModificationService;
import com.api.v2.people.dtos.PersonModificationDto;
import jakarta.validation.Valid;
import org.springframework.modulith.events.ApplicationModuleListener;
import reactor.core.publisher.Mono;

public class DoctorModificationEventListener {

    private final DoctorModificationService doctorModificationService;

    public DoctorModificationEventListener(DoctorModificationService doctorModificationService) {
        this.doctorModificationService = doctorModificationService;
    }

    @ApplicationModuleListener
    public Mono<Void> listen(String medicalLicenseNumber, @Valid PersonModificationDto modificationDto) {
        return doctorModificationService.modify(medicalLicenseNumber, modificationDto);
    }
}
