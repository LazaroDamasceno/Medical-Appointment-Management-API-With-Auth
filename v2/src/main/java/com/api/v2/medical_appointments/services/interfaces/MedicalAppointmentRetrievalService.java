package com.api.v2.medical_appointments.services.interfaces;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentRetrievalService {
    Mono<MedicalAppointmentResponseDto> findById(String id);
    Flux<MedicalAppointmentResponseDto> findAll();
}
