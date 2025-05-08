package com.api.v1.medical_appointments.services.exposed;

import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentUpdatingService {
    Mono<MedicalAppointment> update(MedicalAppointment medicalAppointment);
}
