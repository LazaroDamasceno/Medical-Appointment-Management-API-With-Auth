package com.api.v1.medical_appointments.domain;

import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MedicalAppointmentRepository extends ReactiveMongoRepository<MedicalAppointment, String> {
}
