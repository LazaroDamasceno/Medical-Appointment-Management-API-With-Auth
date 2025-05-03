package com.api.v1.doctors.dtos;

import com.api.v1.doctors.domain.MedicalLicenseNumber;
import com.api.v1.doctors.domain.exposed.Doctor;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DoctorRepository extends ReactiveMongoRepository<Doctor, String> {

    @Query("{ 'medicalLicenseNumber': ?0 }")
    Mono<Doctor> findByMedicalLicenseNumber(MedicalLicenseNumber medicalLicenseNumber);

    @Query("{ 'person.ssn': ?0 }")
    Mono<Doctor> findBySsn(String ssn);

    @Query("{ 'person.email': ?0 }")
    Mono<Doctor> findByEmail(String email);

}
