package com.api.v2.medical_slots

import org.springframework.data.mongodb.repository.MongoRepository

interface MedicalSlotCrudRepository: MongoRepository<MedicalSlot, String>