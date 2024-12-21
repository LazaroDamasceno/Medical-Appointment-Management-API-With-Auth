package com.api.v2.doctors.events;

import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.services.DoctorHiringService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DoctorHiringEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final DoctorHiringService hiringService;

    public DoctorHiringEventPublisher(
            ApplicationEventPublisher eventPublisher,
            DoctorHiringService hiringService
    ) {
        this.eventPublisher = eventPublisher;
        this.hiringService = hiringService;
    }

    public Mono<DoctorResponseDto> publish(DoctorHiringDto hiringDto) {
        return hiringService
                .hire(hiringDto)
                .flatMap(savedDoctor -> {
                   eventPublisher.publishEvent(savedDoctor);
                   return Mono.just(savedDoctor);
                });
    }
}
