package com.api.v1.medical_appointments.services.ordinary_appointments;

import com.api.v1.medical_appointments.responses.ordinary_appointments.MedicalAppointmentResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentRetrievalService {
    Mono<ResponseEntity<MedicalAppointmentResponseDto>> findById(String customerId, String appointmentId);
    ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByCustomer(String customerId, long size);
    ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByDoctor(String doctorLicenseNumber, long size);
    ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAll();
}
