package com.api.v1.medical_appointments.services.ordinary_appointments;

import com.api.v1.medical_appointments.responses.ordinary_appointments.MedicalAppointmentResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalAppointmentRegistrationService {
    Mono<ResponseEntity<MedicalAppointmentResponseDto>> register(String customerId,
                                                                 String doctorLicenseNumber,
                                                                 LocalDateTime bookedAt
    );
}
