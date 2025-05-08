package com.api.v1.medical_appointments.services;

import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalAppointmentRegistrationService {
    Mono<ResponseEntity<MedicalAppointmentResponseDto>> register(String customerId,
                                                                 String doctorId,
                                                                 LocalDateTime bookedAt
    );
}
