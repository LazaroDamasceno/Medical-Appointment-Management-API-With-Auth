package com.api.v1.medical_appointments.domain;

import com.api.v1.customers.domain.exposed.Customer;import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalAppointmentRepository extends ReactiveMongoRepository<MedicalAppointment, String> {

    @Query("{ 'customer._id': ?0, '_id': ?1 }")
    Mono<MedicalAppointment> findById(String customerId, String appointmentId);

    @Query("{ 'customer._id': ?0, 'bookedAt': ?1, 'status': { $eq: 'ACTIVE' } }")
    Mono<MedicalAppointment> findByCustomerAndBookedAt(String customerId, LocalDateTime bookedAt);

    @Query("{ 'customer._id': ?0 }")
    Flux<MedicalAppointment> findAllByCustomer(String customerId);

    @Query("{ 'doctor._id': ?0 }")
    Flux<MedicalAppointment> findAllByDoctor(String doctorId);
}
