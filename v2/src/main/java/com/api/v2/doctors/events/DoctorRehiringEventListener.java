package com.api.v2.doctors.events;

import com.api.v2.doctors.services.DoctorRehiringService;
import org.springframework.modulith.events.ApplicationModuleListener;
import reactor.core.publisher.Mono;

public class DoctorRehiringEventListener {

    private final DoctorRehiringService rehiringService;

    public DoctorRehiringEventListener(DoctorRehiringService rehiringService) {
        this.rehiringService = rehiringService;
    }

    @ApplicationModuleListener
    Mono<Void> rehire(String medicalLicenseNumber) {
        return rehiringService.rehire(medicalLicenseNumber);
    }
}
