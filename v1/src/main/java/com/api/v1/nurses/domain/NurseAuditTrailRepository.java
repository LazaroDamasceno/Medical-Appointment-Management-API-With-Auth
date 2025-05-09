package com.api.v1.nurses.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NurseAuditTrailRepository extends ReactiveMongoRepository<NurseAuditTrail, String> {
}
