package com.api.v1.physicians.repositories;

import com.api.v1.UsState;
import com.api.v1.physicians.models.Physician;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PhysicianRepository extends MongoRepository<Physician, String> {

    Optional<Physician> findByMedicalLicenseIdAndMedicalLicenseState(String id, UsState state);
}
