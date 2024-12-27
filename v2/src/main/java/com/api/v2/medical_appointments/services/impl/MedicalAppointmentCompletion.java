package com.api.v2.medical_appointments.services.impl;

import reactor.core.publisher.Mono;

public interface MedicalAppointmentCompletion {
    Mono<Void> complete(String id);
}
