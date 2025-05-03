package com.api.v1.doctors.services;

import com.api.v1.doctors.responses.DoctorResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DoctorRetrievalService {
    Mono<ResponseEntity<DoctorResponseDto>> findById(String id);
    Flux<ResponseEntity<DoctorResponseDto>> findAll();
}
