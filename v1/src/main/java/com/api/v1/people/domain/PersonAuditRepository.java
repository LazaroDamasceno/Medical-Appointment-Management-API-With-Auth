package com.api.v1.people.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonAuditRepository extends MongoRepository<PersonAuditTrail, String> {
}
