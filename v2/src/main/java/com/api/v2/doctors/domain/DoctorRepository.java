package com.api.v2.doctors.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DoctorRepository extends ReactiveMongoRepository<Doctor, String> {
}
