package com.api.v1.medical_appointments.services.ordinary_appointments.exposed;

import com.api.v1.medical_appointments.domain.ordinaty_appointments.exposed.MedicalAppointment;
import reactor.core.publisher.Mono;

public interface MedicalAppointmentUpdatingService {
    Mono<MedicalAppointment> update(MedicalAppointment medicalAppointment);
}
