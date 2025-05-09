package com.api.v1.medical_appointments.domain;

import com.api.v1.medical_appointments.domain.exposed.EmergyMedicalAppointment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmergyMedicalAppointmentRepository extends ReactiveMongoRepository<EmergyMedicalAppointment, String> {
}
