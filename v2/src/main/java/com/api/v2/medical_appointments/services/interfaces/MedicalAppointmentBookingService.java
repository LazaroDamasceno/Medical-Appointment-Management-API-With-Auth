package com.api.v2.medical_appointments.services.interfaces;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentBookingService {
    Mono<MedicalAppointmentResponseDto> publicInsuranceBook(MedicalAppointmentBookingDto bookingDto);
    Mono<MedicalAppointmentResponseDto> privateInsuranceBook(MedicalAppointmentBookingDto bookingDto);
    Mono<MedicalAppointmentResponseDto> paidByPatientBook(MedicalAppointmentBookingDto bookingDto);
}
