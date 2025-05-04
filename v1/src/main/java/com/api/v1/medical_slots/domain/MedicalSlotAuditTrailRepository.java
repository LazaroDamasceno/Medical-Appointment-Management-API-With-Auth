package com.api.v1.medical_slots.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MedicalSlotAuditTrailRepository extends ReactiveMongoRepository<MedicalSlotAuditTrail, String> {
}
