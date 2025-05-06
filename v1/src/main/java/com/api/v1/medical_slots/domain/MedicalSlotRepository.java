package com.api.v1.medical_slots.domain;

import com.api.v1.doctors.domain.exposed.Doctor;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalSlotRepository extends ReactiveMongoRepository<MedicalSlot, String> {

    @Query("{ 'doctor': ?0, 'availableAt': ?1 }")
    Mono<MedicalSlot> findByDoctorAndAvailableAt(Doctor doctor, LocalDateTime availableAt);

    @Query("{ 'doctor': ?0, '_id': ?1 }")
    Mono<MedicalSlot> findByDoctorAndMedicalSlotId(Doctor doctor, String medicalSlotId);

    @Query("{ 'doctor': ?0 }")
    Flux<MedicalSlot> findByDoctor(Doctor doctor);

    @Query("{ '_id': ?0, 'status': { $eq: 'ACTIVE' } }")
    Mono<MedicalSlot> findActiveById(String medicalSlotId);
}
