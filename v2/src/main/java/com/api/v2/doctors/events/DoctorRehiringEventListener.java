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
    public Mono<Void> listen(String medicalLicenseNumber) {
        return rehiringService.rehire(medicalLicenseNumber);
    }
}
