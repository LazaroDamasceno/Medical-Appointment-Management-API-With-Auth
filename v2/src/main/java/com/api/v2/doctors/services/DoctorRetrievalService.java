package com.api.v2.doctors.services;

import com.api.v2.doctors.dtos.DoctorResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DoctorRetrievalService {
    Mono<HalResourceWrapper<DoctorResponseDto, Void>> findByMedicalLicenseNumber(String medicalLicenseNumber);
    Flux<DoctorResponseDto> findAll();
}
