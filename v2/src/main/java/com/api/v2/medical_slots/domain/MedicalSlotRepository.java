package com.api.v2.medical_slots.domain;

import com.api.v2.customers.domain.Customer;
import com.api.v2.doctors.domain.Doctor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalSlotRepository extends ReactiveMongoRepository<MedicalSlot, ObjectId> {

    @Query("""
        { 'doctor': ?0 },
        { 'availableAt': ?1 },
        { 'canceledAt': null },
        { 'completedAt': null }
    """)
    Mono<MedicalSlot> findActiveByDoctorAndAvailableAt(Doctor doctor, LocalDateTime availableAt);
}
