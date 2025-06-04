package com.api.v1.medical_slots.domain;

import com.api.v1.medical_slots.MedicalSlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;

public interface MedicalSlotCrudRepository extends MongoRepository<MedicalSlot, String> {

    @Query("{ 'doctor._id': ?0, 'availableAt': ?1, 'status': 'ACTIVE' }")
    MedicalSlot findActiveByDoctorAndAvailableAt(String doctorId, LocalDateTime availableAt);
}
