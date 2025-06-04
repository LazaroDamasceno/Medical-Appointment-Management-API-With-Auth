package com.api.v1.medical_slots.domain;

import com.api.v1.medical_slots.MedicalSlot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicalSlotCrudRepository extends MongoRepository<MedicalSlot, String> {
}
