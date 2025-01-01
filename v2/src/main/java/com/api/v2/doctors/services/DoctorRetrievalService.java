package com.api.v2.doctors.services;

import com.api.v2.doctors.dtos.DoctorResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DoctorRetrievalService {
    Mono<DoctorResponseDto> findByMedicalLicenseNumber(String medicalLicenseNumber);
    Flux<DoctorResponseDto> findAll();
}
