package com.api.v1.doctors.services;

import com.api.v1.doctors.responses.DoctorResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DoctorRetrievalService {
    Mono<DoctorResponseDto> findById(String id);
    Flux<DoctorResponseDto> findAll();
}
