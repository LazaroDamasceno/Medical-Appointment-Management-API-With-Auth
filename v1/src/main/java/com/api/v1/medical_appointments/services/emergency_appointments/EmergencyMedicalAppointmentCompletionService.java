package com.api.v1.medical_appointments.services.emergency_appointments;

import com.api.v1.common.EmptyResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface EmergencyMedicalAppointmentCompletionService {
    Mono<ResponseEntity<EmptyResponse>> completed(String doctorLicenseNumber, String customerId);
}
