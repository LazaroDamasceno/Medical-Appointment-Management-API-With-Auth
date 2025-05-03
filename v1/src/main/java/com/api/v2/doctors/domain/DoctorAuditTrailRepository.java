package com.api.v1.doctors.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DoctorAuditTrailRepository extends ReactiveMongoRepository<DoctorAuditTrail, String> {
}
