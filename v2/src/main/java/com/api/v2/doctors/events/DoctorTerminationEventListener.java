package com.api.v2.doctors.events;

import com.api.v2.doctors.services.DoctorTerminationService;
import org.springframework.modulith.events.ApplicationModuleListener;
import reactor.core.publisher.Mono;

public class DoctorTerminationEventListener {

    private final DoctorTerminationService terminationService;

    public DoctorTerminationEventListener(DoctorTerminationService terminationService) {
        this.terminationService = terminationService;
    }

    @ApplicationModuleListener
    public Mono<Void> listen(String medicalLicenseNumber) {
        return terminationService.terminate(medicalLicenseNumber);
    }
}
