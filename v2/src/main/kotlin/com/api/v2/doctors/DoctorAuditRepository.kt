package com.api.v2.doctors

import org.springframework.data.mongodb.repository.MongoRepository

interface DoctorAuditRepository: MongoRepository<DoctorAuditTrail, String>