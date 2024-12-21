package com.api.v2.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DoctorAuditTrailRepository extends ReactiveMongoRepository<DoctorAuditTrail, ObjectId> {
}
