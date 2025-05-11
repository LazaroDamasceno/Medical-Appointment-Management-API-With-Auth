package com.api.v1.medical_appointments.services;

import com.api.v1.medical_appointments.responses.EmergencyMedicalAppointmentResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface EmergencyMedicalAppointmentRegistrationService {
    Mono<ResponseEntity<EmergencyMedicalAppointmentResponseDto>> register(String nurseId, String doctorLicenseNumber, String customerId);
    Mono<ResponseEntity<EmergencyMedicalAppointmentResponseDto>> register(String nurseId, String doctorLicenseNumber);
    Mono<ResponseEntity<EmergencyMedicalAppointmentResponseDto>> register(String doctorLicenseNumber);
}
