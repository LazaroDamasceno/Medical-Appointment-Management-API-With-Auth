package com.api.v2.medical_appointments.services.interfaces;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentRetrievalService {
    Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findById(String id);
    Flux<MedicalAppointmentResponseDto> findAll();
    Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPublicInsuranceByCustomer(String ssn);
    Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>>findAllPrivateInsuranceByCustomer(String ssn);
    Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPaidByPatientByCustomer(String ssn);
}
