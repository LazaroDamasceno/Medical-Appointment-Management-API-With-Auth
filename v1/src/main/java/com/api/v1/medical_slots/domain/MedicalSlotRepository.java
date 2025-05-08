package com.api.v1.medical_slots.domain;

import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalSlotRepository extends ReactiveMongoRepository<MedicalSlot, String> {

    @Query("{ 'doctor._id': ?0, 'availableAt': ?1, 'status': { $eq: 'ACTIVE' } }")
    Mono<MedicalSlot> findActiveByDoctorAndAvailableAt(String doctorId, LocalDateTime availableAt);

    @Query("{ '_id': ?0, 'status': 'ACTIVE' }")
    Mono<MedicalSlot> findActiveById(String slotId);

    @Query("{ 'doctor._id': ?0, '_id': ?1 }")
    Mono<MedicalSlot> findByDoctorAndSlotId(String doctorId, String slotId);

    @Query("{ 'medicalAppointment._id': ?0 }")
    Mono<MedicalSlot> findByMedicalAppointment(String appointmentId);

    @Query("{ 'doctor._id': ?0 }")
    Flux<MedicalSlot> findAllByDoctor(String doctorId);

}
