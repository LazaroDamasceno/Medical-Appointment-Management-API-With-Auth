package com.api.v1.medical_appointments.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmergyMedicalAppointmentAuditTrailRepository
        extends ReactiveMongoRepository<EmergyMedicalAppointmentAuditTrail, String> {}
