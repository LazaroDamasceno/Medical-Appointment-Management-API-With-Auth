package com.api.v2.doctors.dtos;

import com.api.v2.doctors.domain.exposed.Doctor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DoctorRepository extends ReactiveMongoRepository<Doctor, String> {
}
