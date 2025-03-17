package com.api.v2.medical_appointments.services.interfaces;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentBookingService {
    Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> publicInsurance(MedicalAppointmentBookingDto bookingDto);
    Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> privateInsurance(MedicalAppointmentBookingDto bookingDto);
    Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> paidByPatient(MedicalAppointmentBookingDto bookingDto);
}
