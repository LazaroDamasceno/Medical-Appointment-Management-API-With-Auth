package com.api.v1.doctors.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorAuditRepository extends MongoRepository<DoctorAuditTrail, String> {
}
