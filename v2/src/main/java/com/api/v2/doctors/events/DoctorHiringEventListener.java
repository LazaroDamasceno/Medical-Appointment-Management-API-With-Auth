package com.api.v2.doctors.events;

import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.services.DoctorHiringService;
import org.springframework.modulith.events.ApplicationModuleListener;
import reactor.core.publisher.Mono;

public class DoctorHiringEventListener {

    private final DoctorHiringService hiringService;

    public DoctorHiringEventListener(DoctorHiringService hiringService) {
        this.hiringService = hiringService;
    }

    @ApplicationModuleListener
    public Mono<DoctorResponseDto> listen(DoctorHiringDto hiringDto) {
        return hiringService.hire(hiringDto);
    }
}
