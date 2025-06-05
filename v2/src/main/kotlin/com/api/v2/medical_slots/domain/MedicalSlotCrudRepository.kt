package com.api.v2.medical_slots.domain

import com.api.v2.medical_slots.MedicalSlot
import org.springframework.data.mongodb.repository.MongoRepository

interface MedicalSlotCrudRepository: MongoRepository<MedicalSlot, String>