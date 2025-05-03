package com.api.v1.customers.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerAuditTrailRepository extends ReactiveMongoRepository<CustomerAuditTrail, String> {
}
