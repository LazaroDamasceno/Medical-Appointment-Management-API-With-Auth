package com.api.v2.doctors.controllers;

import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.events.DoctorHiringEventPublisher;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/doctors")
public class DoctorController {

    private final DoctorHiringEventPublisher hiringEventPublisher;

    public DoctorController(DoctorHiringEventPublisher hiringEventPublisher) {
        this.hiringEventPublisher = hiringEventPublisher;
    }

    @PostMapping
    public Mono<DoctorResponseDto> hire(@Valid @RequestBody DoctorHiringDto hiringDto) {
        return hiringEventPublisher.publish(hiringDto);
    }
}
