package com.api.v1;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PhysicianRepository extends MongoRepository<Physician, String> {

    @Query("{ 'MedicalLicenseNumber': ?0 }")
    Optional<MedicalLicense> findByMedicalLicense(MedicalLicense medicalLicense);
}
