package com.api.v1.medical_appointments.domain;

import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalAppointmentRepository extends ReactiveMongoRepository<MedicalAppointment, String> {

    @Query("{ 'customer': ?0, '_id': ?1 }")
    Mono<MedicalAppointment> findById(Customer customer, String appointmentId);

    @Query("{ 'customer': ?0, 'bookedAt': ?1 }")
    Mono<MedicalAppointment> findByCustomerAndBookedAt(Customer customer, LocalDateTime bookedAt);

    @Query("{ 'customer': ?0 }")
    Flux<MedicalAppointment> findAllByCustomer(Customer customer);

    @Query("{ 'doctor': ?0 }")
    Flux<MedicalAppointment> findAllByDoctor(Doctor doctor);
}
