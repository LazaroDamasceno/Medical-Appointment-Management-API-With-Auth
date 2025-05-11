package com.api.v1.medical_appointments.domain.emergency_appointments;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmergencyMedicalAppointmentAuditTrailRepository
        extends ReactiveMongoRepository<EmergencyMedicalAppointmentAuditTrail, String> {}
