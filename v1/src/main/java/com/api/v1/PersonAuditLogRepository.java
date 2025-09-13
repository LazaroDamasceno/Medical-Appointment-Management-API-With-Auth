package com.api.v1;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonAuditLogRepository extends MongoRepository<String, Person> {
}
