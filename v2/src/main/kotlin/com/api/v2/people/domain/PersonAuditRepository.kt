package com.api.v2.people.domain

import org.springframework.data.mongodb.repository.MongoRepository

interface PersonAuditRepository: MongoRepository<PersonAuditTrail, String>