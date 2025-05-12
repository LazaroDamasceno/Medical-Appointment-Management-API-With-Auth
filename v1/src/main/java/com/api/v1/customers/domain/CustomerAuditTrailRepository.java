package com.api.v1.customers.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerAuditTrailRepository extends MongoRepository<CustomerAuditTrailRepository, String> {
}
