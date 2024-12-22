package com.api.v2.doctors.events;

import com.api.v2.doctors.services.DoctorRehiringService;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Mono;

public class DoctorRehiringEventPublisher {

    private final DoctorFinderUtil doctorFinderUtil;
    private final DoctorRehiringService rehiringService;
    private final ApplicationEventPublisher eventPublisher;

    public DoctorRehiringEventPublisher(
            DoctorFinderUtil doctorFinderUtil,
            DoctorRehiringService rehiringService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.doctorFinderUtil = doctorFinderUtil;
        this.rehiringService = rehiringService;
        this.eventPublisher = eventPublisher;
    }

    public Mono<Void> publish(String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .zipWith(rehiringService.rehire(medicalLicenseNumber))
                .flatMap(tuple -> {
                    eventPublisher.publishEvent(tuple.getT1());
                    return Mono.empty();
                });
    }
}
