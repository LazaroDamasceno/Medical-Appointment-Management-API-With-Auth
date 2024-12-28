package com.api.v2.medical_appointments.domain;

import com.api.v2.customers.domain.Customer;
import com.api.v2.doctors.domain.Doctor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MedicalAppointmentRepository extends ReactiveMongoRepository<MedicalAppointment, ObjectId> {

    @Query("""
        { 'customer': ?0 },
        { 'doctor': ?1 },
        { 'bookedAt': ?2 },
        { 'canceledAt': null },
        { 'completedAt': null }
    """)
    Mono<MedicalAppointment> findActiveByCustomerAndDoctorAndBookedAt(Customer customer, Doctor doctor, LocalDateTime bookedAt);
}
