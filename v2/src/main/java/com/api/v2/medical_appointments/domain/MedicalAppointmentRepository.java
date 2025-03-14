package com.api.v2.medical_appointments.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MedicalAppointmentRepository extends ReactiveMongoRepository<MedicalAppointment, String> {
}
