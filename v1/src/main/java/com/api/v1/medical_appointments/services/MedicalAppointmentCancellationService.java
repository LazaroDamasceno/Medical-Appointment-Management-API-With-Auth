package com.api.v1.medical_appointments.services;

import com.api.v1.common.EmptyResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentCancellationService {
    Mono<ResponseEntity<EmptyResponse>> cancel(String customerId, String appointmentId);
}
