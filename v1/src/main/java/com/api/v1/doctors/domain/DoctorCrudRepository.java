package com.api.v1.doctors.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface DoctorCrudRepository extends MongoRepository<Doctor, String> {

    @Query("{ 'person.sin': ?0 }")
    Optional<Doctor> findBySIN(String sin);

    @Query("{ 'person.email': ?0 }")
    Optional<Doctor> findByEmail(String email);

    @Query("{ 'licenseNumber': ?0 }")
    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    @Query("{ 'licenseNumber': ?0, 'status': 'TERMINATED' }")
    Optional<Doctor> findTerminatedByLicenseNumber(String licenseNumber);

    @Query("{ 'licenseNumber': ?0, 'status': 'ACTIVE' }")
    Optional<Doctor> findActiveByLicenseNumber(String licenseNumber);
}
