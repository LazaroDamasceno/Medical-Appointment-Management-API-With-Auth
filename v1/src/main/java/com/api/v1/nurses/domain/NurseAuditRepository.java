package com.api.v1.nurses.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NurseAuditRepository extends MongoRepository<NurseAuditTrail, String> {
}
