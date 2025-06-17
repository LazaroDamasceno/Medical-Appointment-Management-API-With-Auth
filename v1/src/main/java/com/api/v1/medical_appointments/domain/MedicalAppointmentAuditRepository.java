package com.api.v1.medical_appointments.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicalAppointmentAuditRepository extends MongoRepository<MedicalAppointmentAuditTrail, String> {
}
