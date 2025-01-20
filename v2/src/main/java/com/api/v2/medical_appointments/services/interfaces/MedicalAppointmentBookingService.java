package com.api.v2.medical_appointments.services.interfaces;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.CompleteMedicalAppointmentResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentBookingService {
    Mono<HalResourceWrapper<CompleteMedicalAppointmentResponseDto, Void>> publicInsuranceBook(MedicalAppointmentBookingDto bookingDto);
    Mono<HalResourceWrapper<CompleteMedicalAppointmentResponseDto, Void>> privateInsuranceBook(MedicalAppointmentBookingDto bookingDto);
    Mono<HalResourceWrapper<CompleteMedicalAppointmentResponseDto, Void>> paidByPatientBook(MedicalAppointmentBookingDto bookingDto);
}
