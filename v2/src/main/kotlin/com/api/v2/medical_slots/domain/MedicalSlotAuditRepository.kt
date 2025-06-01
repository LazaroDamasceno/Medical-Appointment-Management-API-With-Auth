package com.api.v2.medical_slots.domain

import org.springframework.data.mongodb.repository.MongoRepository

interface MedicalSlotAuditRepository: MongoRepository<MedicalSlotAuditTrail, String>