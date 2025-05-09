package com.api.v1.medical_slots.domain;

import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalSlotRepository extends ReactiveMongoRepository<MedicalSlot, String> {

    @Query("{ 'doctor._id': ?0, 'availableAt': ?1, 'status': { $eq: 'ACTIVE' } }")
    Mono<MedicalSlot> findActiveByDoctorAndAvailableAt(String doctorId, LocalDateTime availableAt);

    @Query("{ 'doctor._id': ?0, '_id': ?1 }")
    Mono<MedicalSlot> findByDoctorAndSlotId(String doctorId, String slotId);

    @Query("{ 'medicalAppointment._id': ?0 }")
    Mono<MedicalSlot> findByMedicalAppointment(String appointmentId);

    @Query("{ 'doctor._id': ?0 }")
    Flux<MedicalSlot> findAllByDoctor(String doctorId);

}
