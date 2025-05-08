package com.api.v1.medical_appointments.services;

import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import com.api.v1.medical_appointments.utils.MedicalAppointmentFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentRetrievalServiceImpl implements MedicalAppointmentRetrievalService {

    private final CustomerFinder customerFinder;
    private final MedicalAppointmentFinder medicalAppointmentFinder;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    @Override
    public Mono<ResponseEntity<MedicalAppointmentResponseDto>> findById(String customerId, String appointmentId) {
        return ;
    }

    @Override
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByCustomer(String customerId) {
        return null;
    }

    @Override
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAll() {
        return null;
    }
}
