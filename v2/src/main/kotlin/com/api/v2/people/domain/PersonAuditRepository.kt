package com.api.v2.people.domain

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PersonAuditRepository: CoroutineCrudRepository<PersonAuditTrail, String>