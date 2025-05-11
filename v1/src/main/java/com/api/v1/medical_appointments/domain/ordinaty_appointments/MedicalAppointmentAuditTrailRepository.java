package com.api.v1.medical_appointments.domain.ordinaty_appointments;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MedicalAppointmentAuditTrailRepository extends ReactiveMongoRepository<MedicalAppointmentAuditTrail, String> {
}
