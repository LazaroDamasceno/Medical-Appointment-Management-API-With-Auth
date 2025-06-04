package com.api.v1.medical_slots.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicalSlotAuditRepository extends MongoRepository<MedicalSlotAuditTrail, String> {
}
