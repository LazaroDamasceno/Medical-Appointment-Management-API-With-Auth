package com.api.v1.medical_appointments.domain.emergency_appointments;

import com.api.v1.medical_appointments.domain.emergency_appointments.exposed.EmergencyMedicalAppointment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmergencyMedicalAppointmentRepository extends ReactiveMongoRepository<EmergencyMedicalAppointment, String> {
}
