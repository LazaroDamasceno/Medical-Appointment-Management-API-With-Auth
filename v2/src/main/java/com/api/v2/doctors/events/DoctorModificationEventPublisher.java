package com.api.v2.doctors.events;

import com.api.v2.doctors.services.DoctorModificationService;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.people.dtos.PersonModificationDto;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DoctorModificationEventPublisher {

    private final DoctorFinderUtil doctorFinderUtil;
    private final DoctorModificationService modificationService;
    private final ApplicationEventPublisher eventPublisher;

    public DoctorModificationEventPublisher(
            DoctorFinderUtil doctorFinderUtil,
            DoctorModificationService modificationService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.doctorFinderUtil = doctorFinderUtil;
        this.modificationService = modificationService;
        this.eventPublisher = eventPublisher;
    }

    public Mono<Void> publish(String medicalLicenseNumber, @Valid PersonModificationDto modificationDto) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .zipWith(modificationService.modify(medicalLicenseNumber, modificationDto))
                .flatMap(tuple -> {
                    eventPublisher.publishEvent(tuple.getT1());
                   return Mono.empty();
                });
    }
}
