package com.api.v1.nurses.domain;

import com.api.v1.nurses.domain.exposed.Nurse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface NurseCrudRepository extends MongoRepository<Nurse, String> {

    @Query("{ 'person.sin': ?0 ")
    Optional<Nurse> findBySIN(String sin);

    @Query("{ 'person.email': ?0 }")
    Optional<Nurse> findByEmail(String email);

    @Query("{ 'licenseNumber': ?0 }")
    Optional<Nurse> findByLicenseNumber(String licenseNumber);
}
