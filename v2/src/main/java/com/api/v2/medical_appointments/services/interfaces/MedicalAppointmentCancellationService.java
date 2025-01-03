package com.api.v2.medical_appointments.services.interfaces;

import reactor.core.publisher.Mono;

public interface MedicalAppointmentCancellationService {
    Mono<Void> cancel(String id);
}
