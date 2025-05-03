package com.api.v1.doctors.controllers;

import com.api.v1.doctors.requests.DoctorRegistrationDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.doctors.services.DoctorRegistrationService;
import com.api.v1.doctors.services.DoctorUpdatingService;
import com.api.v1.people.requests.PersonUpdatingDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRegistrationService registrationService;
    private final DoctorUpdatingService updatingService;

    @PostMapping
    public Mono<ResponseEntity<DoctorResponseDto>> register(@Valid @RequestBody DoctorRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{doctorId}")
    public Mono<ResponseEntity<Void>> update(@PathVariable String doctorId, @Valid @RequestBody PersonUpdatingDto updatingDto) {
        return updatingService.update(doctorId, updatingDto);
    }
}
