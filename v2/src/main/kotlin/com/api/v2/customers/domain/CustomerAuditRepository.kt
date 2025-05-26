package com.api.v2.customers.domain

import org.springframework.data.mongodb.repository.MongoRepository

interface CustomerAuditRepository: MongoRepository<CustomerAuditTrail, String>