package com.api.v2.medical_appointments.services.interfaces;

import com.api.v2.medical_appointments.dtos.CompleteMedicalAppointmentResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentRetrievalService {
    Mono<HalResourceWrapper<CompleteMedicalAppointmentResponseDto, Void>> findById(String id);
    Flux<HalResourceWrapper<CompleteMedicalAppointmentResponseDto, Void>> findAll();
    Flux<HalResourceWrapper<CompleteMedicalAppointmentResponseDto, Void>> findAllPublicInsuranceByCustomer(String ssn);
    Flux<HalResourceWrapper<CompleteMedicalAppointmentResponseDto, Void>>findAllPrivateInsuranceByCustomer(String ssn);
    Flux<HalResourceWrapper<CompleteMedicalAppointmentResponseDto, Void>> findAllPaidByPatientByCustomer(String ssn);
}
