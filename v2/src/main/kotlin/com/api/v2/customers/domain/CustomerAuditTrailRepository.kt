package com.api.v2.customers.domain

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CustomerAuditTrailRepository: CoroutineCrudRepository<CustomerAuditTrail, String>