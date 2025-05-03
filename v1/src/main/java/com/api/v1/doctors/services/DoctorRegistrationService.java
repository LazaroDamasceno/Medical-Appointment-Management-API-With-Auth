package com.api.v1.doctors.services;

import com.api.v1.doctors.requests.DoctorRegistrationDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface DoctorRegistrationService {
    Mono<ResponseEntity<DoctorResponseDto>> register(DoctorRegistrationDto registrationDto);
}
