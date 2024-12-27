package com.api.v2.medical_slots.domain;

import com.api.v2.doctors.domain.Doctor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalSlotRepository extends ReactiveMongoRepository<MedicalSlot, ObjectId> {

    @Query("""
        { 'availableAt': ?0 },
        { 'doctor': ?1 },
        { 'canceledAt': null },
        { 'completedAt': null }
    """)
    Mono<MedicalSlot> findActiveByDoctorAndAvailableAt(LocalDateTime availableAt, Doctor doctor);
}
