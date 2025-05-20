package com.api.v1.customers.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerAuditRepository extends MongoRepository<CustomerAuditTrail, String> {
}
