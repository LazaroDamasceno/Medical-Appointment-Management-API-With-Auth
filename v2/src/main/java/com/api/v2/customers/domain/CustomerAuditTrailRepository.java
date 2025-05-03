package com.api.v2.customers.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerAuditTrailRepository extends ReactiveMongoRepository<CustomerAuditTrail, String> {
}
