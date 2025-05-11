package com.api.v1.medical_appointments.domain;

import com.api.v1.medical_appointments.domain.exposed.EmergencyMedicalAppointment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmergencyMedicalAppointmentRepository extends ReactiveMongoRepository<EmergencyMedicalAppointment, String> {
}
