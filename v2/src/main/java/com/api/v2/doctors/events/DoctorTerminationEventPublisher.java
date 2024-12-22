package com.api.v2.doctors.events;

import com.api.v2.doctors.services.DoctorTerminationService;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Mono;

public class DoctorTerminationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final DoctorFinderUtil doctorFinderUtil;
    private final DoctorTerminationService terminationService;

    public DoctorTerminationEventPublisher(
            ApplicationEventPublisher eventPublisher,
            DoctorFinderUtil doctorFinderUtil,
            DoctorTerminationService terminationService
    ) {
        this.eventPublisher = eventPublisher;
        this.doctorFinderUtil = doctorFinderUtil;
        this.terminationService = terminationService;
    }

    public Mono<Void> publish(String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .zipWith(terminationService.terminate(medicalLicenseNumber))
                .flatMap(tuple -> {
                    eventPublisher.publishEvent(tuple.getT1());
                    return Mono.empty();
                });
    }
}
