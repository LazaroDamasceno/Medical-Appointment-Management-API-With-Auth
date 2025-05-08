package com.api.v1.medical_appointments.services;

import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentRetrievalService {
    Mono<ResponseEntity<MedicalAppointmentResponseDto>> findById(String customerId, String appointmentId);
    ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByCustomer(String customerId);
    ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByDoctor(String doctorId);
    ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAll();
}
